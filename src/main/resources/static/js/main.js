'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = null;
var examId = null;
var init = false;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect(event) {
    // Username có thể trống nhưng exam thì phải truyền
    username = document.querySelector('#name').value.trim();
    examId = document.querySelector('#exam').value.trim();

    usernamePage.classList.add('hidden');
    chatPage.classList.remove('hidden');

    var socket = new SockJS('/comment');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected, onError);

    event.preventDefault();
}


function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/exam/'+examId, onMessageReceived);

    // Tell your username to the server
    // Truyền vào id của exam
    stompClient.send("/exam/"+examId+"/initComment",
        {},
        JSON.stringify({examId: examId, username: username})
    )

    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();

    if(messageContent && stompClient) {
        var chatMessage = {
            examId: examId,
            username: username,
            content: messageInput.value,
            parentId: null
            //Nếu reply thì truyền id của parent vào
        };

        stompClient.send("/exam/"+examId+"/comment", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}

function onMessageReceived(payload) {
    let message = JSON.parse(payload.body);

    if (Array.isArray(message)) {
        // Khởi tạo các cmt cũ, do mỗi lần client kết nối đến socket thì đều call getComment nên nó sẽ trả luôn cả list về socket.
        // Nếu chưa khởi show thì mới khởi show list comment cũ đó
        if (!init) {
            for (let i = 0; i < message.length; i++) {
                onShowMessage(message[i], false);
            }
            init = true;
        }
    } else {
        onShowMessage(message, false)
    }

}

function onShowMessage(message, reply) {
    var messageElement = document.createElement('li');

    messageElement.classList.add(reply ? 'reply-message' : 'chat-message');

    var avatarElement = document.createElement('i');
    var avatarText = document.createTextNode(message.fullNameUserCreated);
    avatarElement.appendChild(avatarText);
    avatarElement.style['background-color'] = getAvatarColor(message.username);

    messageElement.appendChild(avatarElement);

    var usernameElement = document.createElement('span');
    var usernameText = document.createTextNode(message.fullNameUserCreated);
    usernameElement.appendChild(usernameText);
    messageElement.appendChild(usernameElement);

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    if (!reply) {
        var input = document.createElement("input");
        input.type = "text";
        input.className = "form-control";
        input.placeholder = "Type to reply...";
        messageElement.appendChild(input);
    }

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;

    if (message.replyComment && message.replyComment.length > 0) {
        for (let i = 0; i < message.replyComment.length; i++) {
            onShowMessage(message.replyComment[i], true);
        }
    }
}

function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }

    var index = Math.abs(hash % colors.length);
    return colors[index];
}

usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)
