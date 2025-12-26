# Message Intake and Queue Identification Flow

```mermaid
flowchart TD
    A[Inbound Message Received] --> A1[Resolve or Create Customer] --> B[Find Active Conversation]
    B -->|Found| C[Reuse Conversation] --> CA[Persist Inbound Message] --> CC
    B -->|Not Found| D{Exists queue mapping configuration?}
    
    D -- Yes --> G[Resolve Mapped Queue]
    D -- No --> H[Resolve Default Queue]
    
    G --> I[Create New Conversation]
    H --> I
    
    --> N[Persist Inbound Message] --> J{Queue Open?}
    
    J -- Yes --> K[Set Status WAITING_FOR_ATTENDANCE] --> Q
    J -- No --> M[Set Status OUT_OF_BUSINESS_HOURS]
    
    M --> T{Out Business Message Enabled?}
    
    T -- YES --> L[Send Out of Business Message] --> Y
    T -- NO --> Y
   
   Y[END FLOW]
   
   Q{ Waiting Message Enabled?}

    Q-- Yes --> R[Send Waiting Message] --> S[Start fetching agent]
    Q -- No --> S
   
   --> U{Exists available agent?}
   
   U -- YES --> V[Assign Agent] --> Z[Set status IN_ATTENDANCE]  --> Y
   U -- NO --> W[Keep status WAITING_FOR_ATTENDANCE] --> |Back to business hour validation| J
  
   CC{Queue Still Open?}

   CC -- YES --> CD[Keep status IN_ATTENDANCE] --> CE[END FLOW]
   CC -- NO --> CG[Set Status OUT_OF_BUSINESS_HOURS]

   CG --> CH{Out Business Message Enabled?}

   CH -- YES --> CJ[Send Out of Business Message] --> CE
   CH -- NO --> CE