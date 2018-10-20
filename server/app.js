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
  name         : String,
  email        : String,
  type         : String,
  credits      : String
});

var complaintSchema = new mongoose.Schema({
  email             : String,
  content           : String,
  tag               : String
});

var User         = mongoose.model("User", userSchema);
var Complaint         = mongoose.model("Complaint", complaintSchema);

// Global variable
var gymkhana = [
                    "iit2017048@iiita.ac.in",
                    "iit2017001@iiita.ac.in", 
                    "iit2017119@iiita.ac.in",
                    "iit2016009@iiita.ac.in",
                    "iit2016095@iiita.ac.in",
                    "iit2016507@iiita.ac.in",
                    "iit2015049@iiita.ac.in",
                    "iit2015057@iiita.ac.in",
                    "ecm2017004@iiita.ac.in",
                    "ihm2017009@iiita.ac.in",
                    "ihm2016001@iiita.ac.in",
                    "ecm2015004@iiita.ac.in",
                    "ibm2014008@iiita.ac.in",
                    "icm2014003@iiita.ac.in",
                    "iec2017075@iiita.ac.in",
                    "iec2017052@iiita.ac.in",
                    "iec2016011@iiita.ac.in",
                    "iec2016016@iiita.ac.in",
                    "iec2015501@iiita.ac.in",
                    "rit2015021@iiita.ac.in",
                    "rit2015078@iiita.ac.in",
                    "lit2017031@iiita.ac.in",
                    "lit2016030@iiita.ac.in",
                    "iro2017006@iiita.ac.in",
                    "ihc2017001@iiita.ac.in",
                    "imb2017002@iiita.ac.in",
                    "pwc2015003@iiita.ac.in",
                    "pmi2015003@iiita.ac.in",
                    "pwc2015004@iiita.ac.in"
                ];
                
var gymkhanaMembers = [
                        "iit2017048@iiita.ac.in",
                        "iit2017001@iiita.ac.in", 
                        "iit2017119@iiita.ac.in",
                        "iit2015057@iiita.ac.in",
                        "ecm2017004@iiita.ac.in",
                        "ihm2017009@iiita.ac.in",
                        "ecm2015004@iiita.ac.in",
                        "ibm2014008@iiita.ac.in",
                        "icm2014003@iiita.ac.in",
                        "iec2017075@iiita.ac.in",
                        "iec2017052@iiita.ac.in",
                        "iec2016011@iiita.ac.in",
                        "iec2016016@iiita.ac.in",
                        "iec2015501@iiita.ac.in",
                        "rit2015021@iiita.ac.in",
                        "rit2015078@iiita.ac.in",
                        "lit2017031@iiita.ac.in",
                        "lit2016030@iiita.ac.in",
                        "ihc2017001@iiita.ac.in",
                        "imb2017002@iiita.ac.in",
                        "pmi2015003@iiita.ac.in",
                        "pwc2015004@iiita.ac.in"
    ];
    
var president = "iit2015049@iiita.ac.in";

var vicePresident = "iro2017006@iiita.ac.in";

var generalSecretary = "ihm2016001@iiita.ac.in";

var speaker = "pwc2015003@iiita.ac.in";

var culturalSecretary = "iit2016095@iiita.ac.in";

var academicSecretary = "iit2016507@iiita.ac.in";

var technicalSecretary = "iit2016009@iiita.ac.in";

var sportsSecretary = "lit2016032@iiita.ac.in";

var studentWelfareSecretary = "iim2016005@iiita.ac.in";

var presidentialCouncilMember = "pwc2015004@iiita.ac.in";

var presidentialCouncilMember = "iit2015057@iiita.ac.in";
    
    
    
    
app.get("/", function (req, res){
    res.send ("api")
})

app.get("/newuser/:name/:email/:type", function(req, res){
    console.log(req.params);
    var type = req.params.type;
    var email = req.params.email;
    email = email.toLowerCase();
    User.create({name: req.params.name, email: req.params.email, type: req.params.type, credits: "0"}, function(err, newuser){
        if(err){
            console.log (err);
        }
        else{
            if (type == "Gymkhana-Secretary"){
                if (email == "IHM2016001@iiita.ac.in" || email == "ihm2016001@iiita.ac.in"){
                    res.send("done")
                } else {
                    res.send("fakeuser");
                }
            }
            else if (type == "Gymkhana-Member"){
                if(gymkhana.includes(email)){
                    res.send("done");
                } else{
                    res.send("fakeuser");
                }
            }
            else{
                res.send("done");
            }
        }
    });
    
})


app.get("/allusers/:type", function (req, res){
    var type = req.params.type;
    User.find({type: type},function(err, founduser){
        if(err){
            console.log(err);
        } else{
            res.send(founduser);
        }
    });
})


app.get("/newcomplaint/:email/:content/:tag", function (req, res){
    Complaint.create({email: req.params.email, content: req.params.content, tag: req.params.tag}, function(err, newuser){
        if(err){
            console.log (err);
        }
        else{
            res.send(newuser);
        }
    });
})

app.get("/user/info/:email", function (req, res){
    User.findOne({email: req.params.email},function(err, founduser){
        if(err){
            console.log(err);
        } else{
            res.send(founduser);
        }
    });
})

app.listen(process.env.PORT,process.env.IP,function(){
    console.log("Server is Up..!!"); 
});