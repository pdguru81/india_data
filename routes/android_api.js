var express = require('express');
var router = express.Router();
var Asha = require('./../model/asha');
var Mother = require('./../model/mother');


/// Authenticates the user and sends a response saying "No logged in user!" if no user is logged in.
function isAuthenticated(req, res, next) {
    if (req.session.user) {
        return next();
    }
    res.json({ error: "No logged in user!", success: false });
};

// Login
router.post('/login/:phone/:/password', function(req, res){
		Asha.findOne({phone: req.params.phone}, function(err, asha){
			if (asha && (req.params.password === asha.password_hash)){
				req.session.user = user;
				res.status(200).json({success: true});
			} else {
				res.status(500).json({success:false, error: "Login failed. Please check your phone and/or password"})
			}
		});
});

// Logout
router.get('/logout', function(req,res){
	if (req.session.user){
		req.session.destroy();
	}
});

// Get records
router.get('/mothers', isAuthenticated, function(req, res){
		Asha.findOne({phone: req.session.user.phone}, function(err, asha){
				Mother.find({_asha: asha._id}, function(err, mothers){
					if (err){
						res.status(500).json({success: false, error: "Could not retrieve list of mothers"});
					} else {
						res.status(200).json({success: true, mothers: mothers});
					}
				});
		});
});

// Get mothers records
router.get('/mothers/:phone', isAuthenticated, function(req, res){

});


module.exports = router;