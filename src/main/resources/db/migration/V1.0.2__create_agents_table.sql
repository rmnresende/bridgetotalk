-- Migration: Create agents table
-- Description: Creates the agents table with UUID generation handled by the database
-- Author: Renan Resende
-- Date: 11/2025

-- Create extension for UUID generation (PostgreSQL)
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create agents table
CREATE TABLE agents (
                        id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                        company_id UUID NOT NULL,
                        name VARCHAR(255) NOT NULL,
                        email VARCHAR(255) NOT NULL,
                        password_hash VARCHAR(255) NOT NULL,
                        role VARCHAR(50) NOT NULL,
                        status VARCHAR(50) NOT NULL,
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        deleted_at TIMESTAMP NULL,

    -- Constraints
                        CONSTRAINT chk_agent_role CHECK (role IN ('ADMIN', 'MANAGER', 'AGENT')),
                        CONSTRAINT chk_agent_status CHECK (status IN ('AVAILABLE', 'BUSY', 'PAUSED', 'OFFLINE'))
);

-- Create indexes
CREATE UNIQUE INDEX idx_agent_email
    ON agents(email)
    WHERE deleted_at IS NULL;

CREATE INDEX idx_agents_company
    ON agents (company_id)
    WHERE deleted_at IS NULL;

-- Create index for soft delete queries
CREATE INDEX idx_agent_active
    ON agents(company_id, status)
    WHERE deleted_at IS NULL;

-- Add comment to table
COMMENT ON TABLE agents IS 'Stores agent (user) information for the customer service platform';
COMMENT ON COLUMN agents.id IS 'Primary key, auto-generated UUID';
COMMENT ON COLUMN agents.company_id IS 'Reference to the company this agent belongs to';
COMMENT ON COLUMN agents.email IS 'Unique email address for login';
COMMENT ON COLUMN agents.role IS 'Agent role: ADMIN, MANAGER, or AGENT';
COMMENT ON COLUMN agents.status IS 'Current agent status: AVAILABLE, BUSY, PAUSED, or OFFLINE';
COMMENT ON COLUMN agents.deleted_at IS 'Soft delete timestamp, NULL if record is active';
