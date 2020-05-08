function call() {
    var video_container = document.getElementById("v-container");
    video_container.setAttribute("style", "display:block;");
    //video_container.style.display = "unset";


    // Generate random room name if needed
    if (!location.hash) {
        //location.hash = Math.floor(Math.random() * 0xFFFFFF).toString(16);
        //location.hash = "";
    }
    //const roomHash = location.hash.substring(1);

    // **********************************************************************************************
    // I think as long as two rooms have the same hash then they can connect
    const roomHash = "telemedicine#000000";

    // TODO: Replace with your own channel ID
    const drone = new ScaleDrone('yiS12Ts5RdNhebyM');
    // Room name needs to be prefixed with 'observable-'
    const roomName = 'observable-' + roomHash;
    const configuration = {
        iceServers: [{
            urls: 'stun:stun.l.google.com:19302'
        }]
    };
    let room;
    let pc;
    let call_active = false;

    function onSuccess() {
        console.log("OnSuccess method executed");
    };

    function onError(error) {
        console.error(error);
    };

    drone.on('open', error => {
        console.log("drone.on listener executed");
        if (error) {
            return console.error(error);
        }
        room = drone.subscribe(roomName);
        room.on('open', error => {
            if (error) {
                onError(error);
            }
        });

        room.on('members', members => {
            console.log('MEMBERS', members);
            // If we are the second user to connect to the room we will be creating the offer
            const isOfferer = members.length === 2;
            startWebRTC(isOfferer);
        });
    });


    // Send signaling data via Scaledrone
    function sendMessage(message) {
        console.log("sendMessage method executed");
        drone.publish({
            room: roomName,
            message
        });
    }


    function startWebRTC(isOfferer) {
        console.log("startWebRTC method executed");
        pc = new RTCPeerConnection(configuration);

        // 'onicecandidate' notifies us whenever an ICE agent needs to deliver a
        // message to the other peer through the signaling server
        pc.onicecandidate = event => {
            if (event.candidate) {
                sendMessage({'candidate': event.candidate});
            }
        };

        // If user is offerer let the 'negotiationneeded' event create the offer
        if (isOfferer) {
            pc.onnegotiationneeded = () => {
                pc.createOffer().then(localDescCreated).catch(onError);
            }
        }

        // When a remote stream arrives display it in the #remoteVideo element
        pc.ontrack = event => {
            const stream = event.streams[0];
            if (!remoteVideo.srcObject || remoteVideo.srcObject.id !== stream.id) {
                remoteVideo.srcObject = stream;
            }
        };

        navigator.mediaDevices.getUserMedia({
            audio: true,
            video: true,
        }).then(stream => {
            // Display your local video in #localVideo element
            localVideo.srcObject = stream;
            //localvideo = stream;
            // Add your stream to be sent to the conneting peer
            stream.getTracks().forEach(track => pc.addTrack(track, stream));
        }, onError);

        // Listen to signaling data from Scaledrone
        room.on('data', (message, client) => {
            // Message was sent by us
            if (client.id === drone.clientId) {
                return;
            }

            if (message.sdp) {
                // This is called after receiving an offer or answer from another peer
                pc.setRemoteDescription(new RTCSessionDescription(message.sdp), () => {
                    // When receiving an offer lets answer it
                    if (pc.remoteDescription.type === 'offer') {
                        pc.createAnswer().then(localDescCreated).catch(onError);
                    }
                }, onError);
            } else if (message.candidate) {
                // Add the new ICE candidate to our connections remote description
                pc.addIceCandidate(
                    new RTCIceCandidate(message.candidate), onSuccess, onError
                );
            }
        });
    }

    function localDescCreated(desc) {
        console.log("local method executed");
        pc.setLocalDescription(
            desc,
            () => sendMessage({'sdp': pc.localDescription}),
            onError
        );
    }
}

// End call onclick event
function endCall(){
    var video_container = document.getElementById("video_container");
    var v_container = document.getElementById("v-container");
    v_container.style.display = "none";


    MediaStreamTrack.

    // navigator.mediaDevices.getUserMedia({
    //     audio: true,
    //     video: true,
    // }).then(stream=>{
    //     stream.getTracks().forEach(track => track.stop());
    // })


    // var remove_modal = document.getElementById("startVideoChat");
    // remove_modal.style.display = "none";
    // var remove_modal = document.getElementsByClassName("modal-backdrop");
    // remove_modal.remove();
    // $('#staticBackdrop').modal('hide')
    //var child = video_container.lastElementChild;
    //while (child) {
    //    video_container.removeChild(child);
    //    child = video_container.lastElementChild;
    // }

    //localVideo.stop();
    //localVideo.srcObject.getTracks()[0].stop();

    console.log("Vid off");
}