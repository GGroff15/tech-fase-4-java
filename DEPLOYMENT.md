# 🚀 GCP Deployment Setup - Quick Start

## Changes Made

### 1. Application Configuration
- **[application.yaml](src/main/resources/application.yaml)**
  - ✅ Added `server.port: ${PORT:8080}` for Cloud Run
  - ✅ Added `server.shutdown: graceful` for clean shutdowns
  - ✅ Configured health endpoints: `management.endpoints.web.exposure.include: health`
  - ✅ Updated secrets to use environment variables: `${GEMINI_API_KEY}`, `${TRIAGE_API_KEY}`

### 2. Docker Configuration
- **[Dockerfile](Dockerfile)**
  - ✅ Added `ENV PORT=8080` and `EXPOSE 8080`
  - ✅ Added `JAVA_TOOL_OPTIONS` for container memory management

### 3. Infrastructure as Code (Terraform)
- **[infra/main.tf](infra/main.tf)** - Core infrastructure (Cloud Run, Artifact Registry, Secret Manager)
- **[infra/variables.tf](infra/variables.tf)** - Configuration variables
- **[infra/outputs.tf](infra/outputs.tf)** - Export values for GitHub Actions
- **[infra/workload-identity.tf](infra/workload-identity.tf)** - Secure GitHub Actions authentication
- **[infra/backend.tf](infra/backend.tf)** - Remote state configuration (optional)
- **[infra/terraform.tfvars.example](infra/terraform.tfvars.example)** - Example configuration

### 4. CI/CD Pipeline
- **[.github/workflows/deploy.yml](.github/workflows/deploy.yml)** - Automated deployment workflow
  - Runs tests with Maven
  - Builds and pushes Docker images to Artifact Registry
  - Deploys to Cloud Run
  - Manual trigger via GitHub Actions UI

### 5. Documentation
- **[infra/README.md](infra/README.md)** - Complete setup guide with commands

### 6. Version Control
- **[.gitignore](.gitignore)** - Added Terraform state and variable files

---

## Next Steps (Do This Before First Deploy)

### Step 1: Create GCP Project
```powershell
# Set your project ID
$PROJECT_ID = "medical-triage-prod"

# Create project
gcloud projects create $PROJECT_ID --name="Medical Triage Production"

# Set as active
gcloud config set project $PROJECT_ID

# Link billing (replace BILLING_ACCOUNT_ID with your actual billing account)
gcloud billing projects link $PROJECT_ID --billing-account=BILLING_ACCOUNT_ID
```

### Step 2: Authenticate
```powershell
gcloud auth login
gcloud auth application-default login
```

### Step 3: Configure Terraform
```powershell
cd infra

# Copy and edit variables
cp terraform.tfvars.example terraform.tfvars
notepad terraform.tfvars

# Set these values:
# project_id  = "medical-triage-prod"          # Your GCP project ID
# github_repo = "yourusername/tech-fase-4"    # Your GitHub repo
```

### Step 4: Deploy Infrastructure
```powershell
# Initialize Terraform
terraform init

# Preview changes
terraform plan

# Apply (creates all infrastructure)
terraform apply
# Type 'yes' when prompted
```

### Step 5: Set Secrets
```powershell
# Add your Gemini API key
$GEMINI_KEY = "your-actual-gemini-api-key-here"
echo $GEMINI_KEY | gcloud secrets versions add gemini-api-key --data-file=-

# Generate and add triage API key
$TRIAGE_KEY = -join ((65..90) + (97..122) + (48..57) | Get-Random -Count 32 | % {[char]$_})
echo $TRIAGE_KEY | gcloud secrets versions add triage-api-key --data-file=-
echo "Save this Triage API Key: $TRIAGE_KEY"
```

### Step 6: Configure GitHub Secrets
```powershell
# Get values from Terraform
terraform output

# Add these to GitHub: Settings → Secrets and variables → Actions → New repository secret
```

**Required GitHub Secrets:**
- `GCP_PROJECT_ID` → Your project ID (e.g., `medical-triage-prod`)
- `GCP_WORKLOAD_IDENTITY_PROVIDER` → From `terraform output workload_identity_provider`
- `GCP_SERVICE_ACCOUNT` → From `terraform output service_account_email`

### Step 7: Deploy Application
1. Commit and push changes:
   ```powershell
   git add .
   git commit -m "Add GCP deployment configuration"
   git push
   ```

2. Go to GitHub:
   - **Actions** tab
   - Select **"Deploy to Cloud Run"**
   - Click **"Run workflow"** → **"Run workflow"**

3. Wait ~3-5 minutes for first deployment

4. Get your service URL:
   ```powershell
   terraform output service_url
   ```

5. Test it:
   ```powershell
   curl (terraform output -raw service_url)/actuator/health
   ```

---

## Cost Estimate

**For a low-traffic MVP:**
- Cloud Run: $0-5/month (2M requests free tier)
- Artifact Registry: $0.50-2/month
- Secret Manager: $0.12-0.50/month

**Total: ~$1-10/month**

Cloud Run scales to zero when not in use, so you only pay for actual usage!

---

## Architecture

```
GitHub Actions (CI/CD)
    ↓
Artifact Registry (Docker Images)
    ↓
Cloud Run (Serverless Container)
    ↓
Secret Manager (API Keys)
```

---

## Support

- **Full setup guide:** See [infra/README.md](infra/README.md)
- **Troubleshooting:** Check the README's troubleshooting section
- **Logs:** `gcloud run services logs tail medical-triage --region=us-central1`

---

## Security Notes

✅ **Secure by default:**
- No long-lived service account keys
- Workload Identity Federation for GitHub Actions
- Secrets stored in Secret Manager
- HTTPS-only endpoints
- Automatic security updates for base images

⚠️ **Before production:**
- Review CORS configuration in [WebConfig](src/main/java/com/tech_challenge/medical/infrastructure/config/WebConfig.java)
- Consider adding authentication for form submission endpoints
- Enable Cloud Armor for DDoS protection
- Set up monitoring alerts

---

**Everything is ready! Follow the steps above to deploy your application to GCP.** 🎉
