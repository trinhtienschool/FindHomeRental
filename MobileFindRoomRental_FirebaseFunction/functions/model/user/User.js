const ConnectDB = require('../connectDB');
class User {
    constructor() { };
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
    }

    deleteUser = async function (userUid) {
        try {
            let isDeleted = await ConnectDB.deleteUser(userUid);
            console.log("isDeleted user: "+isDeleted);
            return isDeleted;
           
        } catch (error) {
            console.log('Error: ' + error);
            return false;
        }
    }
}
module.exports = User;