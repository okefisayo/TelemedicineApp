
// Appointments =================================================================================================
function getAppointments(){
    appointment_database = [
        {"user": "Pedro Rebollar", "date": "Feburary 11, 2020", "time": "2:00 pm - 3:00pm", "description": "This is the description of the appointment"},
        {"user": "Pedro Rebollar", "date": "Feburary 11, 2020", "time": "2:00 pm - 3:00pm", "description": "This is the description of the appointment"},
        {"user": "Pedro Rebollar", "date": "Feburary 11, 2020", "time": "2:00 pm - 3:00pm", "description": "This is the description of the appointment"},
        {"user": "Pedro Rebollar", "date": "Feburary 11, 2020", "time": "2:00 pm - 3:00pm", "description": "This is the description of the appointment"},
        {"user": "Pedro Rebollar", "date": "Feburary 11, 2020", "time": "2:00 pm - 3:00pm", "description": "This is the description of the appointment"},
        {"user": "Pedro Rebollar", "date": "Feburary 11, 2020", "time": "2:00 pm - 3:00pm", "description": "This is the description of the appointment"},
    ];

    // Get todays date and display all the appointments for today
    var recent_appointments = appointment_database

    return appointment_database
}


function displayAppointments(){
    var appointment_side_column = document.getElementById("appointments");

    // Gets all the appointments for today
    all_appointments = getAppointments();

    for (var i=0; i < all_appointments.length; i++) {
        var appointment = document.createElement("div");
        appointment.setAttribute("class", "appointment");

    appointment_side_column.appendChild(appointment);
    }
}

displayAppointments();

// Messages =====================================================================================================
function getMessages(){
    message_database = [
        {"user": "Pedro Rebollar", "date": "Feburary 11, 2020", "time": "2:00 pm - 3:00pm", "description": "This is the description of the appointment"},
        {"user": "Pedro Rebollar", "date": "Feburary 11, 2020", "time": "2:00 pm - 3:00pm", "description": "This is the description of the appointment"},
        {"user": "Pedro Rebollar", "date": "Feburary 11, 2020", "time": "2:00 pm - 3:00pm", "description": "This is the description of the appointment"},
        {"user": "Pedro Rebollar", "date": "Feburary 11, 2020", "time": "2:00 pm - 3:00pm", "description": "This is the description of the appointment"},
        {"user": "Pedro Rebollar", "date": "Feburary 11, 2020", "time": "2:00 pm - 3:00pm", "description": "This is the description of the appointment"},
        {"user": "Pedro Rebollar", "date": "Feburary 11, 2020", "time": "2:00 pm - 3:00pm", "description": "This is the description of the appointment"},
    ];

    // Get todays date and display all the appointments for today
    var recent_messages = message_database

    return message_database
}


function displayMessages(){
    var messages_side_column = document.getElementById("messages");

    // Gets all the appointments for today
    /*
    all_messages = getMessages();

    for (var i=0; i < all_messages.length; i++) {
        var message = document.createElement("div");
        message.setAttribute("class", "appointment");

    messages_side_column.appendChild(message);
    }
    */

    var message_container = document.createElement("div");
    message_container.setAttribute("class", "message_container");
    messages_side_column.appendChild(message_container);
}

displayMessages();

// Patients ===================================================================================================
var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }

    var index = Math.abs(hash % colors.length);
    return colors[index];
}

var user_database;
var patient_database = [];
function displayPatients(user_data){
    var patient_column = document.getElementById("patients");

    var patient_container = document.createElement("div");
    patient_container.setAttribute("class", "patient_container");

    //var all_patients = getPatients();
    user_database = user_data;
    var users = user_database;

    for (var i = 0; i < users.length; i++) {
        if (users[i].user_type === "patient") {
            //patient_database.append(users[i]);
            var patient = users[i].name;

            var li = document.createElement("li");
            li.setAttribute("class", "chat-message");

            var avatarElement = document.createElement('i');
            var avatarText = document.createTextNode(patient[0]);
            avatarElement.appendChild(avatarText);
            avatarElement.style['background-color'] = getAvatarColor(patient);

            var span = document.createElement("span");
            var spanText = document.createTextNode(patient);
            span.appendChild(spanText);

            li.appendChild(avatarElement);
            li.appendChild(span);
            patient_container.appendChild(li);
        }
    }

    patient_column.appendChild(patient_container);
}


