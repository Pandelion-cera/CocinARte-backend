import { NextRequest, NextResponse } from 'next/server';

export async function POST(request: NextRequest) {
  try {
    const formData = await request.formData();
    const imageFile = formData.get('image') as File;

    if (!imageFile) {
      return NextResponse.json(
        { error: 'No image file provided' },
        { status: 400 }
      );
    }

    // Create FormData to send to Java backend
    const javaFormData = new FormData();
    javaFormData.append('image', imageFile);

    // Send to Java backend
    const javaBackendUrl = process.env.JAVA_BACKEND_URL || 'http://cocina-Publi-dRJVfjGAbpfZ-2110027558.us-east-1.elb.amazonaws.com';
    const javaResponse = await fetch(`${javaBackendUrl}/api/recognize/upload`, {
      method: 'POST',
      body: javaFormData,
    });

    if (!javaResponse.ok) {
      const errorText = await javaResponse.text();
      console.error('Java backend error:', errorText);
      return NextResponse.json(
        { error: 'Failed to process image with Java backend' },
        { status: 500 }
      );
    }

    const javaResult = await javaResponse.json();

    if (javaResult.error) {
      return NextResponse.json(
        { error: javaResult.error },
        { status: 500 }
      );
    }

    // Extract the detected food name from Java response
    const detectedFood = javaResult.detectedFood;

    if (!detectedFood) {
      return NextResponse.json(
        { error: 'No food detected in image' },
        { status: 400 }
      );
    }

    return NextResponse.json({
      detectedFood,
      confidence: javaResult.confidence,
      status: 'success'
    });

  } catch (error) {
    console.error('Error recognizing food:', error);
    return NextResponse.json(
      { error: 'Failed to recognize food in image' },
      { status: 500 }
    );
  }
}
