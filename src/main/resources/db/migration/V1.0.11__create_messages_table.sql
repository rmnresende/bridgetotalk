-- Migration: Create message table
-- Description: Creates the message table with FK to conversation
-- Author: Renan Resende
-- Date: 12/2025

CREATE TABLE messages (
        id UUID PRIMARY KEY,
        conversation_id UUID NOT NULL,

        sender_type VARCHAR(30) NOT NULL,
        content TEXT NOT NULL,
        content_type VARCHAR(30) NOT NULL,

        external_id VARCHAR(255),
        status VARCHAR(50),

        agent_id UUID,
        customer_id UUID,

        created_at TIMESTAMP WITH TIME ZONE NOT NULL,

        CONSTRAINT fk_message_conversation FOREIGN KEY (conversation_id)
        REFERENCES conversations(id) ON DELETE CASCADE,

        CONSTRAINT chk_message_content_type CHECK (content_type IN ('TEXT', 'FILE', 'IMAGE', 'SYSTEM', 'AUDIO', 'LOCATION'))
);

CREATE INDEX idx_message_conversation
    ON messages(conversation_id);

CREATE UNIQUE INDEX idx_message_external_id
    ON messages(external_id)
    WHERE external_id IS NOT NULL;
