var express = require('express');
var router = express.Router();

/// Authenticates the user and sends a response saying "No logged in user!" if no user is logged in.
function isAuthenticated(req, res, next) {
    if (req.user) {
        return next();
    }
    res.json({ error: "No logged in user!", success: false });
};


module.exports = router;