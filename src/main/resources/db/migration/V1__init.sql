-- ============================
--  COMPANY
-- ============================

CREATE TABLE company (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(50),
    document VARCHAR(50) NOT NULL UNIQUE,
    status VARCHAR(50) NOT NULL,

    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
    deleted_at TIMESTAMP WITH TIME ZONE
);

CREATE INDEX idx_company_slug ON company (slug);
CREATE INDEX idx_company_status ON company (status);
CREATE INDEX idx_company_document ON company (document);

-- ============================
--  COMPANY SETTINGS (1:1 Shared PK)
-- ============================

CREATE TABLE company_settings (
    company_id UUID PRIMARY KEY REFERENCES company(id) ON DELETE CASCADE,

    max_agents INT NOT NULL DEFAULT 5,
    max_queues INT NOT NULL DEFAULT 5,

    timezone VARCHAR(50) DEFAULT 'UTC' NOT NULL,
    language VARCHAR(10) DEFAULT 'en' NOT NULL,
    plan VARCHAR(50) NOT NULL,

    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL
);
