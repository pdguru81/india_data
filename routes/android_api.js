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
			if (asha && (req.params.password === asha.password)){
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
				Mother.find({asha: asha._id}, function(err, mothers){
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
	Mother.find({phone: req.params.phone}, function(err, mother_records){
		if (err){
			res.status(500).json({success: false, error: "Could not retrieve mother data"});
		} else {
			res.status(200).json({success: true, records: mother_records});
		}
	});
});

router.post('/signup', function(req, res){
	Asha.findOne({phone: phone}, function(err, asha){
		if (asha){
			res.status(500).json({success: false, error: "Asha already exists!"});
		} else {
			res.status(200).json({success:true});
		} 
	})
})


module.exports = router;