# Provision Throughput - Terraform Configuration

## Overview
This Terraform configuration provisions throughput resources in Google Cloud Platform for incident INC0010003.

## Resources Created
- **Firestore Database**: Native mode database with provisioned throughput
- **Cloud Bigtable Instance**: High-performance NoSQL database with 6 nodes
- **Cloud Spanner Instance**: Horizontally scalable relational database with 6000 processing units

## Prerequisites
1. Google Cloud SDK installed and configured
2. Terraform v1.0+ installed
3. Appropriate GCP permissions:
   - Firestore Admin
   - Bigtable Admin
   - Spanner Admin
   - Project IAM Admin

## Usage

### Initialize Terraform
```bash
cd terraform
terraform init
```

### Configure Variables
Copy the example variables file and update with your values:
```bash
cp terraform.tfvars.example terraform.tfvars
```

Edit `terraform.tfvars`:
```hcl
project_id       = "your-actual-project-id"
region           = "us-central1"
throughput_count = 6
```

### Plan the Deployment
```bash
terraform plan
```

### Apply the Configuration
```bash
terraform apply
```

### Verify Resources
After successful deployment, verify the resources in Google Cloud Console:
- Firestore: https://console.cloud.google.com/firestore
- Bigtable: https://console.cloud.google.com/bigtable
- Spanner: https://console.cloud.google.com/spanner

## Cost Estimation
Provisioned throughput resources incur costs based on:
- Bigtable: ~$0.65/hour per node (6 nodes = ~$3.90/hour)
- Spanner: ~$0.90/hour per node (6 nodes = ~$5.40/hour)
- Firestore: Based on operations and storage

**Estimated Total**: ~$9.30/hour or ~$6,700/month

## Cleanup
To remove all provisioned resources:
```bash
terraform destroy
```

**Note**: Some resources have `deletion_protection` enabled. You'll need to disable it before destroying.

## Incident Reference
- **Incident Number**: INC0010003
- **Description**: Purchase 6 Provision Throughput from Google Cloud
- **Assigned To**: Sumit K
- **Created**: 2025-12-07

## Support
For questions or issues, contact the infrastructure team or update incident INC0010003.
