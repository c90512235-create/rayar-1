const express = require("express");
const bodyparser = require("body-parser");
const sqlite3 = require("sqlite3").verbose();
const bcrypt = require("bcrypt");
const jwt = require("jsonwebtoken");
const cors = require("cors");
require("dotenv").config();

const app = express();
app.use(cors());
app.use(bodyparser.json());
app.use(express.static("public"));

const secret ="Mubede_secret";

const db =new sqlite3.Database("datebase.db");

/////// datebase tables creation
db.serialize(()=>{
    db.run('GREATE TABLE IF NOT EXISTS users(
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        username TEXT NOT NULL UNIQUE,
        password TEXT NOT NULL,
        role  TEXT NOT NULL
    )
    ');
    db.run('
    GREATE TABLE IF NOT EXISTS payments(
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        student_name or id TEXT NOT NULL,
        amount REAL NOT NULL,
        currency TEXT NOT NULL,
        payment_date TEXT NOT NULL,
        status TEXT NOT NULL,
        date TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
        user_id INTEGER,
        FOREIGN KEY(user_id) REFERENCES users(id)
         )
    
    ');
});

////////////// admin routes
const adminsRoute = adminsRoute () =>{
    const adminLogin = {
        RAYAN :"789456123",
        NEWTON :"123456789",
        
    }
    for (adminLogin){
         const {username ,password} = req.body;
         if(adminLogin[username]===password){
            const token = jwt.sign({username,role:"admin"},secret,{expiresIn:"1h"});
            res.json({token});
         }

    }

    ////////// user 
const seedUsers =()=>{
    const users = [
        {username:"RAYAN",password:"789456123",role:"admin"},
        {username:"NEWTON",password:"123456789",role:"admin"},
        {username:"Kawala Joy",password:"any"}
        {username:"Alpha",password:"any"}
        {username:"Ericom",password:"any"}
        {username:"FixItpro",password:"any"} ............................................
       
    ]
    users.forEach(user => {
        db.run(`INSERT INTO users (username,password,role)
      VALUES (?,?,?)`,user.username,user.password,user.role);
      [u[0],hash, u[2]]
    });
}

//Users routes (); // RUN ONCE THEN COMMENT
/*----------------------LOGIN------------------
*/
   const {}=app.post("/login",(req,res)=>{
    const {username,password}=req.body;

    db.get("SELECT * FROM users WHERE username = ?",username,(err,Users)=>{
        if(err){
            res.status(500).json({message:"Database error"});
            return;
        }
        if(!Users.password == password || !Users.role == "admin"){
            res.status(401).json({message:"Invalid username or password"});
            return;
        }
        bcrypt.compare(password,Users.password,(err,result)=>{
            if(err){
                res.status(500).json({message:"Database error"});
                return;
            }
            if(!result){
                res.status(401).json({message:"Invalid username or password"});
                return;
            }
            const token = jwt.sign({username,role:row.role},secret,{expiresIn:"1h"});
            res.json({token});
        });
    });
   }); 
 /*-----------------------AUTH MIDDLEWARE------------------ */
 const authenticateToken= (req,res,next) =>{
    const authHeader = req.headers.authorization;
    if(!authHeader || !authHeader.startswith("Bearer ")){
        return res.status(401).json({message:"Unauthorized"});
    }
    const token = authHeader.split(" ")[1];
    jwt.verify(token,secret,(err,user)=>{
        if(err){
            return res.status(403).json({message:"Forbidden"});
        }
        req.user=user;
        next();
    });
 };
 /*----------------------USER ROUTES------------------ */
app.get("/users",auth, (req,res)=> {
    if(req.user.role !== "admin"){
        return res.sendstatus(403).json({message:"Forbidden"});
    }
    db.all("SELECT id, username, role FROM users",[],(err,rows)=>{
        res.json(rows);
     });
})

    /*------------Payments routes ---------------------  */
     /*----------- Get all payments-----------*/
     app.post("/pay",(req,res) => {
        const { student_name, amount,"PAID" ,new,DATE().toLocaleString()},
        Function () {
            res.json({ success: true, message:"Payment recorded successfully"
        
            });
        }
     })
      app.get("/payments",authenticateToken,(req,res)=>{
        if(req.user.role !== "admin"){
            return res.sendstatus(403).json({message:"Forbidden"});
        }
        db.all("SELECT * FROM payments ORDER BY payment_date DESC",[],(err,rows)=>{
            res.json(rows);
        });
      })

      /*------------START THE SERVER--------*/
      const PORT = process.env.PORT || 3000;
      app.listen(PORT,()=>{
        console.log(`server is running on port ${PORT}`);
      });



    
