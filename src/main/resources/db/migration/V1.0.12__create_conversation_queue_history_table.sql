-- Migration: Create conversation_queue_history table
-- Description: Creates the table to persist conversation queue history
-- Author: Renan Resende
-- Date: 12/2025

CREATE TABLE conversation_queue_history (
    id UUID PRIMARY KEY,
    conversation_id UUID NOT NULL,
    from_queue_id UUID,
    to_queue_id UUID NOT NULL,
    transferred_by_agent_id UUID,
    reason VARCHAR(50) NOT NULL,
    details TEXT,
    transferred_at TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT fk_cqh_conversation FOREIGN KEY (conversation_id) REFERENCES conversations(id),
    CONSTRAINT fk_cqh_from_queue FOREIGN KEY (from_queue_id) REFERENCES queues(id),
    CONSTRAINT fk_cqh_to_queue FOREIGN KEY (to_queue_id) REFERENCES queues(id),
    CONSTRAINT fk_cqh_transfer_by_agent FOREIGN KEY (transferred_by_agent_id) REFERENCES agents(id),
    CONSTRAINT chk_reason CHECK (reason IN ('MANUAL', 'AUTOMATIC_ROUTING', 'OUT_OF_BUSINESS_HOURS', 'SYSTEM', 'SKILL_MATCH', 'OVERFLOW'))

);

CREATE INDEX idx_conversation_id
    ON conversation_queue_history(conversation_id);

CREATE INDEX idx_cqh_transferred_at
    ON conversation_queue_history(transferred_at);
