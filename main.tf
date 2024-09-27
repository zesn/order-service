terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.16"
    }
  }

  required_version = ">= 1.2.0"
}
provider "aws" {
    region = "${var.region}"
    access_key = "${var.access_key}"
    secret_key = "${var.secret_key}"
}
resource "aws_instance" "app_server" {
  ami           = "ami-06f855639265b5541" 
  key_name = "dixita1"
  instance_type = "t2.micro"

  tags = {
    Name = "zesn-ec2"
  }
}