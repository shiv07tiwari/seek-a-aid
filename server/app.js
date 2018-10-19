const express        = require ("express");
const app            = express();
const bodyParser     = require("body-parser");
const mongoose       = require("mongoose");

app.use(express.static("public"));

//setup body-parser
app.use(bodyParser.urlencoded({extended : true}));


// connect to mongodb database on mlab
mongoose.connect("mongodb://admin:swastik0310@ds237373.mlab.com:37373/prototype", function(){
    console.log("connected to mongodb");
});


var userSchema = new mongoose.Schema({
  name               : String,
  email              : String,
  type               : String,
});

var User         = mongoose.model("User", userSchema);


app.get("/", function (req, res){
    res.send ("api")
})

app.get("/newuser/:name/:email/:type", function(req, res){
    console.log(req.params);
    User.create({name: req.params.name, email: req.params.email, type: req.params.type}, function(err, newuser){
        if(err){
            console.log (err);
        }
        else{
            res.send("done");
        }
    });
    
})


app.get()


app.listen(process.env.PORT,process.env.IP,function(){
    console.log("Server is Up..!!"); 
});