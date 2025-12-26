-- Migration: Create conversations table
-- Description: Creates the conversations table with UUID generation handled by the database
-- Author: Renan Resende
-- Date: 12/2025

CREATE TABLE conversations (
        id UUID PRIMARY KEY,
        company_id UUID NOT NULL,
        customer_id UUID NOT NULL,
        agent_id UUID,
        current_queue_id UUID NOT NULL,
        channel_id UUID NOT NULL,

        status VARCHAR(50) NOT NULL,
        priority VARCHAR(50),

        protocol VARCHAR(100),

        started_at TIMESTAMP WITH TIME ZONE,
        created_at TIMESTAMP WITH TIME ZONE NOT NULL,
        updated_at TIMESTAMP WITH TIME ZONE,

        metadata JSONB,

        CONSTRAINT fk_conversation_company FOREIGN KEY (company_id) REFERENCES companies(id),
        CONSTRAINT fk_conversation_customer FOREIGN KEY (customer_id) REFERENCES customers(id),
        CONSTRAINT fk_conversation_queue FOREIGN KEY (current_queue_id) REFERENCES queues(id),
        CONSTRAINT fk_conversation_agent FOREIGN KEY (agent_id) REFERENCES agents(id),
        CONSTRAINT fk_conversation_channel FOREIGN KEY (channel_id) REFERENCES channels(id),
        CONSTRAINT chk_conversation_status CHECK (status IN ('OUT_OF_BUSINESS_HOURS', 'WAITING_FOR_ATTENDANCE', 'IN_ATTENDANCE', 'PENDING', 'WAITING_FOR_CUSTOMER', 'CLOSED'))
);

CREATE INDEX idx_conversation_active_customer
    ON conversations(company_id, customer_id)
    WHERE status <> 'CLOSED';

CREATE INDEX idx_conversation_queue_status
    ON conversations(current_queue_id, status);

