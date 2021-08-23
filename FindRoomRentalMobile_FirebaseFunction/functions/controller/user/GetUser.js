var axios = require("axios");

const User = require('../../model/user/User');
const ConnectDB = require('../../model/connectDB');

class GetUser {
  constructor() {

  }
  //get all users
  listAllUsers = async function () {
    try {
      let user_json = await ConnectDB.listAllUsers();
      console.log("CO VO DAY CHO 1: ");
      if (user_json != null || user_json != undefined) {
          console.log('CO VO DAY IN JSON');
          console.log("user_json_string: " + user_json)
          return JSON.parse(user_json);
      } else return null;
  } catch (error) {
      console.log('Error: ' + error);
      return null;
  }


   

  };
 //get user
 getUser = async function (userUid) {
  try {
    let user = await ConnectDB.getUser(userUid);
    console.log("userINGetUser; "+user.toString());
    if(user !=null || user !=undefined){
    console.log("user: "+user);
    return JSON.parse(user);
    }else return null;
   
} catch (error) {
    console.log('Error: ' + error);
    return null;
}
};
  //delete user
  deleteUser = async function (userUid) {


    try {
      let isDeleted = await ConnectDB.deleteUser(userUid);
      console.log("isDeleted user: "+isDeleted);
      return isDeleted;
     
  } catch (error) {
      console.log('Error: ' + error);
      return false;
  }
  };
};
module.exports = new GetUser();