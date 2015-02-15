var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');

var routes = require('./routes/index');
var users = require('./routes/users');
var android_api = require('./routes/android_api');

var mongoose = require('mongoose');
var session = require('express-session');
var MongoStore = require('connect-mongo')(session);

var app = express();

var connection_string = 'mongodb://localhost/udapradesh_pregnancy_data';

if (process.env.OPENSHIFT_MONGODB_DB_PASSWORD) {
        connection_string = 'mongodb://' + 
        process.env.OPENSHIFT_MONGODB_DB_USERNAME + ':' +
        process.env.OPENSHIFT_MONGODB_DB_PASSWORD + '@' +
        process.env.OPENSHIFT_MONGODB_DB_HOST + ':' +
        process.env.OPENSHIFT_MONGODB_DB_PORT + '/udapradesh_pregnancy_data';
}

mongoose.connect(connection_string);

var db = mongoose.connection;

db.on('error',function(msg){
    console.log("Mongoose connection error %s", msg);
});

db.on('open',function(){
    console.log("Mongoose connection established!");
})

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');


// uncomment after placing your favicon in /public
//app.use(favicon(__dirname + '/public/favicon.ico'));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());

app.use(session({
    secret: 'indian_asha',
    store: new MongoStore({
        mongoose_connection: db
    })
}));

app.use('/nodepub/'express.static(path.join(__dirname, 'public')));
app.use('/static'express.static(path.join(__dirname, 'public')))
app.use('/node/', express.static(path.join(__dirname, 'public')));
//console.log(express.static(path.join(__dirname, 'public')), " :express static")
app.use('/', routes);
app.use('//users', users);
//app.use('/asha', android_api);
app.use('//asha', android_api);


// catch 404 and forward to error handler
app.use(function(req, res, next) {
    var err = new Error('Not Found');
    err.status = 404;
    next(err);
});

// error handlers

// development error handler
// will print stacktrace
if (app.get('env') === 'development') {
    app.use(function(err, req, res, next) {
        res.status(err.status || 500);
        res.render('error', {
            message: err.message,
            error: err
        });
    });
}

// production error handler
// no stacktraces leaked to user
app.use(function(err, req, res, next) {
    res.status(err.status || 500);
    res.render('error', {
        message: err.message,
        error: {}
    });
});


module.exports = app;
