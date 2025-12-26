-- Migration: Create customers table
-- Description: Creates the channels table with FK to company and default queue
-- Author: Renan Resende
-- Date: 12/2025

CREATE TABLE customers (
         id UUID PRIMARY KEY,
         company_id UUID NOT NULL,

         name VARCHAR(255),
         email VARCHAR(255),
         phone VARCHAR(50),
         documents VARCHAR(50),

         channel_identifier VARCHAR(255),
         status VARCHAR(50),

         created_at TIMESTAMP WITH TIME ZONE NOT NULL,
         updated_at TIMESTAMP WITH TIME ZONE,
         deleted_at TIMESTAMP WITH TIME ZONE,

         CONSTRAINT fk_customer_company FOREIGN KEY (company_id) REFERENCES companies(id)
);

CREATE INDEX idx_company_customer
    ON customers(company_id);

CREATE UNIQUE INDEX idx_channel_customer_company
    ON customers(company_id, channel_identifier)
    WHERE deleted_at IS NULL;
