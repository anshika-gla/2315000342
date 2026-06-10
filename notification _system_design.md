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





# Stage 3

## Query Analysis

Given Query:

```sql
SELECT *
FROM notifications
WHERE studentID = 1042
AND isRead = false
ORDER BY createdAt ASC;
```

### Is the Query Accurate?

The query is functionally correct if the notifications table stores student-specific notifications and contains the columns:

- studentID
- isRead
- createdAt

However, using `SELECT *` is not recommended because it retrieves unnecessary columns and increases I/O cost.

---

## Why is the Query Slow?

Current Scale:

- Students: 50,000
- Notifications: 5,000,000

Without proper indexing, the database performs a full table scan.

Complexity:

```text
O(N)
```

Where:

```text
N = 5,000,000 rows
```

The database scans every row before filtering and sorting.

---

## Optimized Query

```sql
SELECT id,
       notificationType,
       message,
       createdAt
FROM notifications
WHERE studentID = 1042
AND isRead = false
ORDER BY createdAt ASC;
```

---

## Recommended Composite Index

```sql
CREATE INDEX idx_student_read_created
ON notifications(studentID, isRead, createdAt);
```

Benefits:

- Faster filtering by studentID
- Faster filtering by isRead
- Faster sorting by createdAt

Expected Complexity:

```text
O(log N)
```

instead of

```text
O(N)
```

---

## Should We Add Indexes on Every Column?

No.

Adding indexes on every column is not effective.

### Problems

1. Increased storage usage
2. Slower INSERT operations
3. Slower UPDATE operations
4. Slower DELETE operations
5. Unused indexes waste resources

Indexes should only be created on:

- Frequently filtered columns
- Frequently sorted columns
- Join columns

---

## Students Who Received Placement Notifications In Last 7 Days

```sql
SELECT DISTINCT studentID
FROM notifications
WHERE notificationType = 'Placement'
AND createdAt >= NOW() - INTERVAL '7 DAYS';
```

---

## Additional Scaling Improvements

1. Pagination
2. Table Partitioning
3. Redis Caching
4. Archiving Old Notifications
5. Read Replicas

These techniques reduce query latency and improve database scalability.