ALTER TABLE company_settings
ADD COLUMN max_concurrent_conversations_per_agent INT NOT NULL DEFAULT 3;
