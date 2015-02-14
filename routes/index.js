var express = require('express');
var router = express.Router();
var controller = require('./../controller/logic');


// Load the twilio module
var twilio = require('twilio');
 


/* GET home page. */
router.get('/', function(req, res) {

  res.render('index', { title: 'Express' });
});


var sendMessage = function(receiver, message){
  var client = new twilio.RestClient('AC2248e5ffba7f9869f989f9899398df45','dd7dc226a811c76dae44d84382929286');
 
// Pass in parameters to the REST API using an object literal notation. The
// REST client will handle authentication and response serialzation for you.
client.sms.messages.create({
    to : receiver,
    from:'+12107873198',
    body: message

}, function(error, message) {
    if (!error) {       
        res.json({status:200,message:'successfully sent'});
    } else {
        console.log('Oops! There was an error.');
    }
});
}

router.post('/send_message',function(req,res){
  // Create a new REST API client to make authenticated requests against the
// twilio back end
var message = req.body.message;
var receiver = req.body.receiver; 
sendMessage(receiver,message);

});

router.get('/receive',function(req,res){
sendMessage('+16175105270','God is Good');  
  res.type('text/xml');
  res.send('<Response><Say>Hello there! Thanks for calling.</Say></Response>');
});

router.post('/ashas', function(req,res){
  console.log('the call came here!');
  controller.ashaSignUP(req.body.name,req.body.phone, req.body.hospital, req.body.password,res);

});

module.exports = router;
