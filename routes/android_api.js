var express = require('express');
var router = express.Router();
var Asha = require('./../model/asha');


/// Authenticates the user and sends a response saying "No logged in user!" if no user is logged in.
function isAuthenticated(req, res, next) {
    if (req.user) {
        return next();
    }
    res.json({ error: "No logged in user!", success: false });
};

router.post('/login', function(req, res){
		
});


module.exports = router;