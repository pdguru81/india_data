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
		Asha.findOne({phone: }, function(err, asha){
			if (asha && (password_hash === asha.password_hash)){
				req.session.user = user;
				res.status(200).json({success: true});
			} else {
				res.status(500).json({success:false, error: "Login failed. Please check your phone and/or password"})
			}
		});
});


module.exports = router;