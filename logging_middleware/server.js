const express = require("express");
const logger = require("./middleware/logger");

const app = express();

app.use(logger);

app.get("/", (req, res) => {
    res.send("Logging Middleware Working");
});

app.get("/students", (req, res) => {
    res.json({
        message: "Students API Working"
    });
});

app.listen(3000, () => {
    console.log("Server Started on Port 3000");
});