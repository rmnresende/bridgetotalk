-- =========================================================
-- Migration: Create queue_settings table
-- Description: Stores configuration settings for queues
-- Author: Renan Resende
-- Date: 12/2025
-- =========================================================

CREATE TABLE queue_settings (
                                queue_id UUID PRIMARY KEY,

    -- Welcome message
                                welcome_message TEXT,
                                welcome_enabled BOOLEAN NOT NULL DEFAULT FALSE,

    -- Off-hours message
                                off_hours_message TEXT,
                                off_hours_enabled BOOLEAN NOT NULL DEFAULT FALSE,

    -- Waiting message
                                waiting_message TEXT,
                                waiting_enabled BOOLEAN NOT NULL DEFAULT FALSE,

    -- Weekly schedule configuration
                                weekly_schedule JSONB NOT NULL,

    -- Inactivity and Waiting limits
                                auto_close_after_inactivity INTERVAL,
                                max_waiting_time INTERVAL,

    -- Constraints
                                CONSTRAINT fk_queue_settings_queue
                                    FOREIGN KEY (queue_id)
                                        REFERENCES queues(id)
                                        ON DELETE CASCADE
);

-- ---------------------------------------------------------
-- Indexes
-- ---------------------------------------------------------

-- Optional index for JSONB operations (future-proofing)
-- In the future can facilitate more complex queries, like find open queues now, or find queues with a specific schedule
-- CREATE INDEX idx_queue_settings_weekly_schedule
-- ON queue_settings
--    USING GIN (weekly_schedule);

-- ---------------------------------------------------------
-- Comments
-- ---------------------------------------------------------

COMMENT ON TABLE queue_settings IS
    'Configuration settings associated with a queue (1:1 relationship)';

COMMENT ON COLUMN queue_settings.queue_id IS
    'Primary key and foreign key referencing queues(id)';

COMMENT ON COLUMN queue_settings.weekly_schedule IS
    'Weekly business hours configuration stored as JSON';

COMMENT ON COLUMN queue_settings.auto_close_after_inactivity IS
    'Time duration after which an inactive conversation is automatically closed';

COMMENT ON COLUMN queue_settings.max_waiting_time IS
    'Maximum time a customer can wait in queue before an action is triggered';
