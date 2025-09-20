# CocinARte Java Backend Deployment Guide

## Overview
This guide helps you deploy the CocinARte Java backend using AWS Copilot with RDS MySQL and GitHub Actions CI/CD.

## Prerequisites
- AWS CLI configured with appropriate credentials
- AWS Copilot CLI installed
- Docker installed (for local testing)
- GitHub repository with secrets configured

## GitHub Secrets Setup
Add these secrets to your GitHub repository (Settings > Secrets and variables > Actions):

```
AWS_ACCESS_KEY_ID=your_aws_access_key
AWS_SECRET_ACCESS_KEY=your_aws_secret_key
```

## Manual Deployment Steps

### 1. Initialize Copilot Application (if not done)
```bash
cd java
copilot app init cocinarte
```

### 2. Deploy Environment
```bash
copilot env deploy --name production
```

### 3. Deploy Service
```bash
copilot svc deploy --name api --env production
```

### 4. Get Public URL
```bash
copilot svc show --name api --env production
```

## Architecture

### Services
- **Load Balanced Web Service**: Public-facing Java Spring Boot API
- **RDS MySQL**: Managed database with automated backups
- **Application Load Balancer**: Handles SSL termination and routing
- **VPC**: Private networking with public/private subnets

### Environment Variables
The following environment variables are automatically configured:
- `DB_ENDPOINT`: RDS MySQL endpoint
- `DB_PORT`: Database port (3306)
- `DB_NAME`: Database name (cocinarte)
- `DB_USERNAME`: Database username (admin)
- `DB_PASSWORD`: Auto-generated secure password
- `MAIL_USERNAME`: Email service username
- `MAIL_PASSWORD`: Email service password
- `SPRING_PROFILES_ACTIVE`: Set to 'production'
- `CORS_ALLOWED_ORIGINS`: Configured for Amplify

## API Endpoints

Once deployed, your API will be available at: `https://api.{random}.{region}.elb.amazonaws.com`

### Available Endpoints:
- `GET /api/recognize/test` - Health check
- `POST /api/recognize/upload` - Upload image for food recognition
- `GET /api/recognize/model-info` - Model information
- `GET /api/recognize/predict-test` - Test with sample image

## CI/CD Pipeline

The GitHub Actions pipeline automatically:
1. **Tests**: Runs Maven tests on pull requests
2. **Builds**: Creates Docker image with Maven build
3. **Deploys**: Deploys to AWS using Copilot CLI
4. **Reports**: Comments on PRs with deployment URL

### Triggering Deployment
- Push to `main` branch triggers automatic deployment
- Only changes in `java/` directory trigger the pipeline
- Pull requests run tests but don't deploy

## Database Configuration

### RDS MySQL Instance
- Engine: MySQL 8.0.35
- Instance: db.t3.micro (upgrade to db.t3.small+ for production)
- Storage: 20GB GP2 (encrypted)
- Backup: 7-day retention
- Multi-AZ: Disabled (enable for production)

### Connection Details
Database credentials are automatically generated and stored in AWS Systems Manager Parameter Store:
- Endpoint: `/copilot/cocinarte/production/secrets/DB_ENDPOINT`
- Username: `/copilot/cocinarte/production/secrets/DB_USERNAME`
- Password: `/copilot/cocinarte/production/secrets/DB_PASSWORD`

## Local Development

### Build and Test
```bash
cd java
./mvnw clean test
./mvnw clean package
```

### Run with Docker
```bash
cd java
docker build -t cocinarte-api .
docker run -p 8080:8080 cocinarte-api
```

## Troubleshooting

### View Logs
```bash
copilot svc logs --name api --env production --follow
```

### Check Service Status
```bash
copilot svc status --name api --env production
```

### Update Service
```bash
copilot svc deploy --name api --env production
```

### Delete Resources
```bash
copilot svc delete --name api --env production
copilot env delete --name production
copilot app delete cocinarte
```

## Production Considerations

1. **Database**: Upgrade to larger instance class and enable Multi-AZ
2. **Security**: Configure proper CORS origins for your Amplify domain
3. **Monitoring**: Enable CloudWatch detailed monitoring
4. **Backup**: Verify RDS backup retention meets your requirements
5. **SSL**: Copilot automatically provides SSL certificates via ACM

## Cost Optimization

- Use db.t3.micro for development (included in free tier)
- Enable RDS auto-scaling for production workloads
- Consider reserved instances for predictable workloads
- Monitor CloudWatch costs and set up billing alerts

## Support

For issues with:
- **AWS Copilot**: Check [official documentation](https://aws.github.io/copilot-cli/)
- **Spring Boot**: Review application logs via `copilot svc logs`
- **Database**: Check RDS CloudWatch metrics
- **CI/CD**: Review GitHub Actions workflow runs