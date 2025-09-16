import { NextRequest, NextResponse } from 'next/server';
import OpenAI from 'openai';

const openai = new OpenAI({
  apiKey: process.env.OPENAI_API_KEY,
});

export async function POST(request: NextRequest) {
  try {
    const { foodName } = await request.json();

    if (!foodName) {
      return NextResponse.json(
        { error: 'Food name is required' },
        { status: 400 }
      );
    }

    const assistantId = process.env.OPENAI_ASSISTANT_ID;

    if (!assistantId) {
      return NextResponse.json(
        { error: 'OpenAI Assistant ID not configured' },
        { status: 500 }
      );
    }

    // Create a thread
    const threadResponse = await openai.beta.threads.create();
    console.log('Thread created:', threadResponse);

    // Add a message to the thread
    await openai.beta.threads.messages.create(threadResponse.id, {
      role: 'user',
      content: `¿Cuáles son los ingredientes de ${foodName}? Por favor proporciona la respuesta en formato json como una lista de ingredientes solamente.`,
    });

    // Run the assistant
    console.log('Creating run with assistant ID:', assistantId);
    const runResponse = await openai.beta.threads.runs.create(threadResponse.id, {
      assistant_id: assistantId,
    });

    console.log('Run created:', runResponse);

    // Loop until finished processing (exact pattern from working project)
    let run = await openai.beta.threads.runs.retrieve(
      threadResponse.id,
      runResponse.id,
    );

    while (['in_progress', 'queued'].includes(run.status)) {
      await new Promise((resolve) => setTimeout(resolve, 1000));
      console.log(`\tOpenAi is ${run.status}...`);
      run = await openai.beta.threads.runs.retrieve(
        threadResponse.id,
        runResponse.id,
      );
    }

    if (run.status !== 'completed') {
      console.log('Open Ai failed', run);
      throw new Error(`Assistant run ${run.status}`);
    }

    console.log('\tOpenAi completed');

    // Retrieve the assistant's response (exact pattern from working project)
    const messagesResponse = await openai.beta.threads.messages.list(
      threadResponse.id,
    );

    const assistantResponses = messagesResponse.data.filter(
      (msg) => msg.role === 'assistant',
    );

    const response = assistantResponses
      .map((msg) =>
        msg.content
          .filter((contentItem) => contentItem.type === 'text')
          .map((textContent) => textContent.text.value)
          .join('\n'),
      )
      .join('\n');

    const responseJson = JSON.parse(response);
    const ingredients = responseJson?.ingredients || [];

    console.log('Response has length', ingredients.length);

    return NextResponse.json({ ingredients });
  } catch (error) {
    console.error('Error analyzing ingredients:', error);

    return NextResponse.json(
      { error: 'Failed to analyze ingredients' },
      { status: 500 }
    );
  }
}