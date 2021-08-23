const Location = require('../../model/room/location');
const GoogleAPI = require('../../model/room/googleAPI');
const ConnectDB = require('../../model/connectDB');
class SearchEngine{
    constructor(){

    }
    getAPIKey = async function () {
      if(this.key === undefined || this.key ===null){
        this.key = await ConnectDB.getAPIKey();
      }
      return this.key;     
  }
    getNearbyRoomID =async function(address,distanceSearch){
       console.log("location : "+Location);
        let location = new Location(undefined,address,undefined,undefined,undefined);
        var locationsNearBy = [];
        try {
          let key = await this.getAPIKey();
          let locationSearch =await GoogleAPI.convertCoordinate(location.address,key);
          if(locationSearch === undefined || locationSearch === null){
            locationSearch = new Location(null, null,null,10.7581199,106.6908249);
            // location.lat = 10.3243423;
        // location.lng = 100.342342534;
          }
          let allLocations =await ConnectDB.getAllLocations();
          for(let i=0;i<allLocations.length;i++){
            let a_location = allLocations[i];
            let lat = a_location.lat;
            let lng = a_location.lng;
            let distance =  GoogleAPI.getDistanceFromLatLonInKm(locationSearch.lat,locationSearch.lng,lat,lng);
            if(distance<=distanceSearch){
              let location_distance = new Array();
              location_distance.push(distance);
              location_distance.push(a_location);
              
                //location_distance;
                // console.log("Location: "+location_distance);

              locationsNearBy.push(location_distance);
            }
          }
        for(let i = 0;i<locationsNearBy.length;i++){
            console.log(locationsNearBy[i][0]);
        }
        let key2 = await this.getAPIKey();
        console.log("Key: "+key2);
          return this.convertJson(locationsNearBy);
        //   return locationsNearBy;
        } catch (error) {
          console.log("co vo 2");
          console.log(error);
          return null;
        }
      
        // let locations = location.searchNearByRoomID(distance);
        // return this.convertJson(locations);
        // }catch(error){
        //     console.log(error);
        //     return null;
        // }
    }
    convertJson(locations){
        var json = '{"roomIDs":[';
        if(locations.length == 0){
          json +='{},';
        }
        for(let i=0;i<locations.length;i++){
            let a_location = locations[i];
            let location_object = a_location[1];
            let location = '{';
            location +='"roomID"'+':"'+location_object.roomID+'",';
            location +='"address"'+':"'+location_object.address+'",';
            location +='"lat"'+':'+location_object.lat+',';
            location +='"lng"'+':'+location_object.lng+',';
            location +='"isDeleted"'+':'+false+',';
            location += '"distance"'+":"+a_location[0];
            location +='},';
            json+=location;
        }
        json = json.substring(0,json.length-1);
        json+="]}";
        console.log(json);
        return JSON.parse(json);
    }
}
module.exports = new SearchEngine();