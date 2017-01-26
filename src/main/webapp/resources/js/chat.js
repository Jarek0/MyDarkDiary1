
 $( document ).ready(function() {
    console.log( "ready!" );
});


            
function  changeColor(block,contextPath,conversationId) {
    $(".hyperspan").css("background-color", "white");
    $(block).css("background-color","grey");
    $.ajax({
        url : contextPath+'/message/getMessages/'+conversationId,
        method : 'GET',
        async : false,
        success : function(data, textStatus, xhr) {
            
            console.log("SUCCESS: ",data);
        },
        error: function (data, textStatus, xhr) {
        console.log("FAIL: ", data);
      }
        });
    }
            
jQuery(document).ready(function($){
    $('.conteiner').perfectScrollbar();
    $('.conteiner2').perfectScrollbar();
    changeColor('#first');
    }
    );
                    
/* var stompClient = null;
            
             
            function connect() {
                var socket = new SockJS('/spring-mvc-java/chat');
                stompClient = Stomp.over(socket);  
                stompClient.connect({}, function(frame) {
                    //there is function which change displayed messages for conversation
                    console.log('Connected: ' + frame);
                    stompClient.subscribe('/user/' + userName + '/reply', function(messageOutput) {
                        showMessageOutput(JSON.parse(messageOutput.body));
                    });
                });
            }
             
            function disconnect() {
                if(stompClient != null) {
                    stompClient.disconnect();
                }
                console.log("Disconnected");
            }
             
            function sendMessage() {
                var from = document.getElementById('from').value;
                var text = document.getElementById('text').value;
                stompClient.send("/app/chat", {}, 
                  JSON.stringify({'from':from, 'text':text}));
            }
             
            function showMessageOutput(messageOutput) {
                var response = document.getElementById('response');
                var p = document.createElement('p');
                p.style.wordWrap = 'break-word';
                p.appendChild(document.createTextNode(messageOutput.from + ": " 
                  + messageOutput.text + " (" + messageOutput.time + ")"));
                response.appendChild(p);
            }*/
            
   
   
    

