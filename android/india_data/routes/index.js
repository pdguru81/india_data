var express = require('express');
var router = express.Router();
var controller = require('./../controller/logic');

/* GET home page. */
router.get('/', function(req, res) {
  res.render('index', { title: 'Express' });
});

module.exports = router;



router.post('/ashas', function(req,res){
	console.log('the call came here!');
	controller.ashaSignUP(req.body.name,req.body.phone, req.body.hospital, req.body.password,res);

});