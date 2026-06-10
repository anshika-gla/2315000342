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