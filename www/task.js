var task = {
    createEvent: function(successCallback, errorCallback) {
        cordova.exec(
            successCallback, // success callback function
            errorCallback, // error callback function
            'Task', // mapped to our native Java class called "CalendarPlugin"
            'addTaskEntry', // with this action name
            [{                  // and this array of custom arguments to create our entry
               
            }]
        ); 
    }
}
module.exports = task;
