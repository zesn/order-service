# Terraform Configuration for Google Cloud Provision Throughput
# Incident: INC0010003
# Description: Purchase 6 Provision Throughput from Google Cloud

terraform {
  required_providers {
    google = {
      source  = "hashicorp/google"
      version = "~> 5.0"
    }
  }
}

provider "google" {
  project = var.project_id
  region  = var.region
}

# Variables
variable "project_id" {
  description = "Google Cloud Project ID"
  type        = string
}

variable "region" {
  description = "Google Cloud Region"
  type        = string
  default     = "us-central1"
}

variable "throughput_count" {
  description = "Number of throughput provisions to purchase"
  type        = number
  default     = 6
}

# Firestore Database with Provisioned Throughput
resource "google_firestore_database" "provision_throughput_db" {
  project                     = var.project_id
  name                        = "provision-throughput-db"
  location_id                 = var.region
  type                        = "FIRESTORE_NATIVE"
  concurrency_mode            = "OPTIMISTIC"
  app_engine_integration_mode = "DISABLED"

  # Enable point-in-time recovery
  point_in_time_recovery_enablement = "POINT_IN_TIME_RECOVERY_ENABLED"

  # Delete protection
  delete_protection_state = "DELETE_PROTECTION_ENABLED"
}

# Provisioned Throughput Configuration
resource "google_firestore_field" "provision_throughput_config" {
  count = var.throughput_count

  project    = var.project_id
  database   = google_firestore_database.provision_throughput_db.name
  collection = "throughput-collection-${count.index + 1}"
  field      = "__name__"

  index_config {
    indexes {
      order       = "ASCENDING"
      query_scope = "COLLECTION"
    }
  }
}

# Cloud Bigtable Instance with Provisioned Throughput
resource "google_bigtable_instance" "provision_throughput_instance" {
  name                = "provision-throughput-instance"
  project             = var.project_id
  deletion_protection = true

  cluster {
    cluster_id   = "provision-throughput-cluster-1"
    zone         = "${var.region}-a"
    num_nodes    = var.throughput_count
    storage_type = "SSD"
  }

  labels = {
    environment = "production"
    purpose     = "provision-throughput"
    incident    = "inc0010003"
  }
}

# Cloud Bigtable Table
resource "google_bigtable_table" "provision_throughput_table" {
  name          = "provision-throughput-table"
  instance_name = google_bigtable_instance.provision_throughput_instance.name
  project       = var.project_id

  column_family {
    family = "throughput-data"
  }

  lifecycle {
    prevent_destroy = true
  }
}

# Cloud Spanner Instance with Provisioned Capacity
resource "google_spanner_instance" "provision_throughput_spanner" {
  name             = "provision-throughput-spanner"
  project          = var.project_id
  config           = "regional-${var.region}"
  display_name     = "Provision Throughput Spanner Instance"
  processing_units = var.throughput_count * 1000  # 1000 processing units per node

  labels = {
    environment = "production"
    purpose     = "provision-throughput"
    incident    = "inc0010003"
  }
}

# Cloud Spanner Database
resource "google_spanner_database" "provision_throughput_db_spanner" {
  instance = google_spanner_instance.provision_throughput_spanner.name
  name     = "provision-throughput-database"
  project  = var.project_id

  ddl = [
    "CREATE TABLE ThroughputData (Id INT64 NOT NULL, Data STRING(MAX)) PRIMARY KEY (Id)",
  ]

  deletion_protection = true
}

# Outputs
output "firestore_database_name" {
  description = "Name of the Firestore database"
  value       = google_firestore_database.provision_throughput_db.name
}

output "bigtable_instance_name" {
  description = "Name of the Bigtable instance"
  value       = google_bigtable_instance.provision_throughput_instance.name
}

output "spanner_instance_name" {
  description = "Name of the Spanner instance"
  value       = google_spanner_instance.provision_throughput_spanner.name
}

output "total_provisioned_nodes" {
  description = "Total number of provisioned throughput nodes"
  value       = var.throughput_count
}
