var express = require('express');
var router = express.Router();
var Asha = require('./../model/asha');
var Mother = require('./../model/mother');
var Record = require('./../model/record');
var controller = require('./../controller/logic');




/// Authenticates the user and sends a response saying "No logged in user!" if no user is logged in.
function isAuthenticated(req, res, next) {
    if (req.session.user) {
        return next();
    }
    res.json({ error: "No logged in user!", success: false });
};

// Login
router.post('/login', function(req, res){
		console.log("login in with phone" +  req.params.phone)
		Asha.findOne({phone: req.params.phone}, function(err, asha){
			if (asha && (req.params.password === asha.password)){
				req.session.user = asha;
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

// Get records of mothers under ashas care
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
router.get('/mothers/:id', isAuthenticated, function(req, res){
	Record.find({mother: req.params.id}).populate(['asha', 'mother']).exec(function(err, mother_records){
		if (err){
			res.status(500).json({success: false, error: "Could not retrieve mother data"});
		} else {
			var records = mother_records.map(formatAsha);
			console.log("RECORDS: " + records);
			res.status(200).json({success: true, records: records});
		}
	});
});

// Create new record
router.post('/records/:id', isAuthenticated, function(req, res){

	Mother.findById(req.params.id, function(err, mother){
		if (err){
			res.status(500).json({success: false, error: "Database error"});
		} else if (mother) {
			Asha.findOne({phone: req.session.user.phone}, function(err, asha){
				if (asha){
					var record = new Record({
							asha: asha._id,
							mother: mother._id,
							timestamp: new Date(),
							mother_status: {},
							baby_status: {},
							is_confirmed: false
					});
					record.save(function(err, record){
							if (!err){
								res.status(200).json({success: true});
							}
					});
				} else {
					res.status(500).json({success: false, error: "Database error"});
				}			
			});
		} else {
			res.status(500).json({success: false, error: "Could not create record"});
		}
	})
});


//router.post('/mothers', isAuthenticated, function(req, res){
router.post('/mothers',  function(req, res){
		var asha_phone =req.session.user.phone;
		controller.createMothers(asha_phone,req.body.name,req.body.phone,req.body.emergency_contact_phone,res);
});

// Helper functions

var formatAsha = function(record){
	record.asha = {
		id: record.asha._id,
		name: record.asha.name,
		phone: record.asha.phone,
		hospital: record.asha.hospital
	}
	return record;
}

module.exports = router;