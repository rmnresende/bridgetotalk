-- Migration: Create channels table
-- Description: Creates the channels table with FK to company and default queue
-- Author: Renan Resende
-- Date: 12/2025

CREATE TABLE channels (
        id UUID PRIMARY KEY,
        company_id UUID NOT NULL,

        type VARCHAR(50) NOT NULL,
        name VARCHAR(255) NOT NULL,
        external_identifier VARCHAR(255) NOT NULL,

        default_queue_id UUID NOT NULL,
        active BOOLEAN NOT NULL DEFAULT true,

        created_at TIMESTAMP WITH TIME ZONE NOT NULL,
        updated_at TIMESTAMP WITH TIME ZONE,

        CONSTRAINT fk_channel_company FOREIGN KEY (company_id) REFERENCES companies(id),
        CONSTRAINT fk_channel_queue FOREIGN KEY (default_queue_id) REFERENCES queues(id),
        CONSTRAINT chk_channel_type CHECK (type IN ('WHATSAPP', 'TELEGRAM', 'WEB_CHAT', 'INSTAGRAM', 'EMAIL'))
);

CREATE UNIQUE INDEX uq_channel_identifier
    ON channels(company_id, type, external_identifier);
