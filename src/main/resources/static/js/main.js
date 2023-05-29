'use strict';


var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('#connecting');

var connectedUser = document.getElementById("user-container");
var guest_id = connectedUser.dataset.guest;
var organizer_id = connectedUser.dataset.organizer;
var alias = connectedUser.dataset.alias;

var stompClient = null;
var username = null;
var eventoFinal_id = null;

var colors = [
    '#0000dd', '#800040', '#007d00', '#d70500',
    '#939300', '#f00054', '#cc6600', '#6900d2'
];

function connect() {
    username = document.querySelector('#username').innerText.trim();
    eventoFinal_id = document.querySelector('#eventoFinal_id').value.trim();

    var path = document.getElementById('chat-container');
    var endpoint = path.dataset.url + 'ws';	//endpoint parametrizado con la url pasada en el html

    var socket = new SockJS(endpoint);
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected, onError);
}

// Conecta al webSocket Server.
connect();

function onConnected() {
    // Suscripción a Public Topic
    stompClient.subscribe('/topic/publicChatRoom', onMessageReceived);

    // Envía el username al servidor
    stompClient.send("/app/chat.addUser", {},
        JSON.stringify({
            sender: username,
            evento_id: eventoFinal_id,
            type: 'JOIN'
        })
    )

    connectingElement.classList.add('hidden');
}

function onError(error) {
    connectingElement.textContent = 'No se pudo conectar al WebSocket server. Por favor, refresque la página e inténtelo de nuevo!';
    connectingElement.style.color = 'red';
}

function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if (messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            evento_id: eventoFinal_id,
            content: messageInput.value,
            type: 'CHAT'
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    // usuario se une a la reunión
    if (message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
        var icon_join = document.createElement('span');
        icon_join.setAttribute("style", "color:#679d35; margin-right:6px;");
        icon_join.innerHTML = '<i class="fas fa-user-check"></i>';
        messageElement.appendChild(icon_join);
        
    // usuario abandona la reunión
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
        var icon_left = document.createElement('span');
        icon_left.setAttribute("style", "color:#797979; margin-right:6px;");
        icon_left.innerHTML = '<i class="fas fa-user-times"></i>';
        messageElement.appendChild(icon_left);
    
    // se elimina un mensaje
    } else if (message.type === 'DELETE') {
        var delete_id = message.chatMessage_id; // id del mensaje que se quiere eliminar
        var chat_line = document.querySelector(`[data-id="${delete_id}"]`);	// se busca en el DOM el <li> con el id del mensaje a eliminar
        chat_line.classList.add('delete-message');
        chat_line.innerHTML = '<i class="fas fa-ban"></i>' + ' Deleted message by ' + alias + '!'; //se modifica el texto
        
    } else {
        var usernameElement = document.createElement('strong');
        usernameElement.classList.add('nickname');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        usernameElement.style['color'] = getAvatarColor(message.sender);
        var timeElement = document.createElement('span'); // se añade la hora exacta a la que se envía el mensaje
        var messageTime = document.createTextNode(new Date().toLocaleTimeString(navigator.language, {
            hour: '2-digit',
            minute:'2-digit',
            second: '2-digit'
          }));
        timeElement.appendChild(messageTime);
        timeElement.classList.add('time-message');
        messageElement.appendChild(usernameElement);
        messageElement.appendChild(timeElement);
    }

    var textElement = document.createElement('p'); //párrafo con el texto del mensaje
    var messageText = document.createTextNode(message.content);
    var message_id = message.chatMessage_id;
    messageElement.dataset.id = message_id;
    textElement.appendChild(messageText);

    var btn = document.createElement('button'); //botón para eliminar el mensaje, sólo visible para el jefe de proyecto
    btn.innerHTML = '<i class="far fa-trash-alt"></i>';
    btn.setAttribute("style", "background-color:white; margin:10px; width:12px; height:12px; border: none; outline:none;");
    btn.onclick = function deleteMessage() {

        if (confirm("Quiere borrar este mensaje?") == true) {

            var chatMessage = {
                chatMessage_id: message_id,
                type: 'DELETE'
            };
            stompClient.send("/app/chat.deleteMessage", {}, JSON.stringify(chatMessage));
            messageInput.value = '';
        }
    }

    if (message.type != 'JOIN' && message.type != 'LEAVE' && message.type != 'DELETE') {
        if (guest_id === organizer_id) {
            textElement.appendChild(btn);
        }
    }
    
    messageElement.appendChild(textElement);
    
    if (message.type !== 'DELETE') {
    	messageArea.appendChild(messageElement);
    }
    
    messageArea.scrollTop = messageArea.scrollHeight;
}

// función para asignar colores distintos a cada participante en la reunión
function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}

messageForm.addEventListener('submit', sendMessage, true);