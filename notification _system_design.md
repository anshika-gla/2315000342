# Stage 1

## Notification System API Design

### Core Actions

1. Fetch Notifications
2. Create Notification
3. Mark Notification as Read
4. Mark All Notifications as Read
5. Delete Notification
6. Broadcast Notification
7. Real-Time Notification Delivery

---

## 1. Fetch Notifications

### Endpoint

GET /api/notifications

### Headers

```http
Authorization: Bearer <token>
```

### Response

```json
{
  "notifications": [
    {
      "id": "uuid",
      "type": "Placement",
      "message": "CSX Corporation hiring",
      "isRead": false,
      "timestamp": "2026-04-22T17:51:18Z"
    }
  ]
}
```

---

## 2. Create Notification

### Endpoint

POST /api/notifications

### Request Body

```json
{
  "type": "Placement",
  "message": "CSX Corporation hiring"
}
```

### Response

```json
{
  "message": "Notification Created Successfully"
}
```

---

## 3. Mark Notification As Read

### Endpoint

PATCH /api/notifications/{id}/read

### Response

```json
{
  "message": "Notification marked as read"
}
```

---

## 4. Mark All Notifications As Read

### Endpoint

PATCH /api/notifications/read-all

### Response

```json
{
  "message": "All notifications marked as read"
}
```

---

## 5. Delete Notification

### Endpoint

DELETE /api/notifications/{id}

### Response

```json
{
  "message": "Notification deleted successfully"
}
```

---

## 6. Broadcast Notification

### Endpoint

POST /api/notifications/broadcast

### Request Body

```json
{
  "message": "Placement drive announced"
}
```

### Response

```json
{
  "message": "Broadcast sent successfully"
}
```

---

## Real-Time Notification Mechanism

Technology: WebSocket (Socket.IO)

Workflow:

1. Student logs in.
2. Client establishes WebSocket connection.
3. Server maintains active socket connections.
4. When a notification is created, server pushes it instantly.
5. Notification appears without page refresh.

Advantages:

- Real-time updates
- Reduced API polling
- Better user experience




# Stage 2

## Database Selection

Recommended Database: PostgreSQL

### Why PostgreSQL?

- ACID compliant
- Reliable transactions
- Strong indexing support
- Scales well for notification systems
- Supports partitioning for large datasets

---

## Database Schema

### Users Table

```sql
CREATE TABLE users (
    id UUID PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(255) UNIQUE
);
```

### Notifications Table

```sql
CREATE TABLE notifications (
    id UUID PRIMARY KEY,
    type VARCHAR(50),
    message TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### User Notifications Table

```sql
CREATE TABLE user_notifications (
    id UUID PRIMARY KEY,
    user_id UUID REFERENCES users(id),
    notification_id UUID REFERENCES notifications(id),
    is_read BOOLEAN DEFAULT FALSE,
    read_at TIMESTAMP NULL
);
```

---

## Queries

### Fetch Notifications

```sql
SELECT *
FROM notifications
ORDER BY created_at DESC;
```

### Mark Notification As Read

```sql
UPDATE user_notifications
SET is_read = TRUE
WHERE notification_id = 'notification_id';
```

### Create Notification

```sql
INSERT INTO notifications(id, type, message)
VALUES(uuid_generate_v4(), 'Placement', 'CSX Corporation hiring');
```

---

## Scaling Challenges

### Problems

1. Large notification volume
2. Slow queries
3. High storage consumption
4. Increased read load

### Solutions

1. Indexing on timestamp
2. Pagination
3. Table partitioning
4. Archiving old notifications
5. Redis caching