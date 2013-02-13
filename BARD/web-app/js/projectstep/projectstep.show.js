var Renderer = function(canvas){
    var template = Handlebars.compile($("#node-selection-template").html())

    var canvas = $(canvas).get(0)
    var ctx = canvas.getContext("2d");
    var particleSystem
    var isClicked = false
    var that = {
        init:function(system){
            //
            // the particle system will call the init function once, right before the
            // first frame is to be drawn. it's a good place to set up the canvas and
            // to pass the canvas size to the particle system
            //
            // save a reference to the particle system for use in the .redraw() loop
            particleSystem = system

            // inform the system of the screen dimensions so it can map coords for us.
            // if the canvas is ever resized, screenSize should be called again with
            // the new dimensions
            particleSystem.screenSize(canvas.width, canvas.height)
            particleSystem.screenPadding(80) // leave an extra 80px of whitespace per side
            particleSystem.screenStep( 1 );

            // set up some event handlers to allow for node-dragging
            that.initMouseHandling()
        },

        redraw:function(){
            if (isClicked)
                particleSystem.stop()

            // redraw will be called repeatedly during the run whenever the node positions
            // change. the new positions for the nodes can be accessed by looking at the
            // .p attribute of a given node. however the p.x & p.y values are in the coordinates
            // of the particle system rather than the screen. you can either map them to
            // the screen yourself, or use the convenience iterators .eachNode (and .eachEdge)
            // which allow you to step through the actual node objects but also pass an
            // x,y point in the screen's coordinate system
            //
            ctx.fillStyle = "white"
            ctx.fillRect(0,0, canvas.width, canvas.height)

            particleSystem.eachEdge(function(edge, pt1, pt2){
                // edge: {source:Node, target:Node, length:#, data:{}}
                // pt1:  {x:#, y:#}  source position in screen coords
                // pt2:  {x:#, y:#}  target position in screen coords

                // draw a line from pt1 to pt2
                ctx.strokeStyle = "rgba(0,0,0, .333)"
                ctx.lineWidth = 3
                ctx.beginPath()
                ctx.moveTo(pt1.x, pt1.y)
                ctx.lineTo(pt2.x, pt2.y)
                ctx.closePath()
                ctx.stroke()

                // draw arrow
                ctx.save()
                // move to the head position of the edge we just drew
                var wt = ctx.lineWidth
                var arrowLength = 10 + wt
                var arrowWidth = 5 + wt
                ctx.fillStyle = "rgba(255, 0, 0, 1)"

                // Rotate the context to point along the path
                var dx = pt2.x-pt1.x, dy=pt2.y-pt1.y, len=Math.sqrt(dx*dx+dy*dy);
                ctx.translate(pt2.x,pt2.y);
                ctx.rotate(Math.atan2(dy,dx))

                // delete some of the edge that's already there (so the point isn't hidden)
                ctx.clearRect(-arrowLength/2,-wt/2, arrowLength/2,wt)

                // draw
                ctx.beginPath();
                ctx.moveTo(-arrowLength, arrowWidth);
                ctx.lineTo(0, 0);
                ctx.lineTo(-arrowLength, -arrowWidth);
                ctx.lineTo(-arrowLength * 0.8, -0);
                ctx.closePath()
                ctx.fill()
                ctx.restore()

                ctx.fillStyle = "black";
                ctx.font = 'italic 13px sans-serif';
                ctx.fillText (edge.data.name, (pt1.x + pt2.x) / 2, (pt1.y + pt2.y) / 2);
            })

            particleSystem.eachNode(function(node, pt){
                // node: {mass:#, p:{x,y}, name:"", data:{}}
                // pt:   {x:#, y:#}  node position in screen coords
                // draw a rectangle centered at pt
                var w = 10
                ctx.fillStyle = (node.data.alone) ? "orange" : "black"
                ctx.fillRect(pt.x-w/2, pt.y-w/2, w,w)
                ctx.font = 'italic 13px sans-serif';
                ctx.fillText (node.data.name, pt.x+8, pt.y);
                ctx.fillText (node.data.name1, pt.x, pt.y+15);
            })
        },

        initMouseHandling:function(){
            // no-nonsense drag and drop (thanks springy.js)
            var dragged = null;

            // set up a handler object that will initially listen for mousedowns then
            // for moves and mouseups while dragging
            var handler = {
                clicked:function(e){
                    isClicked = true
                    var pos = $(canvas).offset();
                    _mouseP = arbor.Point(e.pageX-pos.left, e.pageY-pos.top)
                    nearest = dragged = particleSystem.nearest(_mouseP);

                    if (dragged && dragged.node !== null){
                        // while we're dragging, don't let physics move the node
                        dragged.node.fixed = true
                    }
                    var projectId = $('#projectIdForStep').val()

                    var selected = (nearest.distance < 50) ? nearest : null
                    resetAfterClick()

                    var fromSelectedNode =  particleSystem.getEdgesFrom(selected.node)
                    var toSelectedNode =  particleSystem.getEdgesTo(selected.node)

                    var params = {fromSelectedNode: fromSelectedNode, toSelectedNode: toSelectedNode, selected: selected, projectId: projectId}
                    $('#node-selection-details').html(template(params))

                    $(canvas).bind('mousemove', handler.dragged)
                    $(window).bind('mouseup', handler.dropped)

                    return false
                },
                dragged:function(e){
                    var pos = $(canvas).offset();
                    var s = arbor.Point(e.pageX-pos.left, e.pageY-pos.top)

                    if (dragged && dragged.node !== null){
                        var p = particleSystem.fromScreen(s)
                        dragged.node.p = p
                    }
                    return false
                },

                dropped:function(e){
                    if (dragged===null || dragged.node===undefined) return
                    if (dragged.node !== null) dragged.node.fixed = false
                    dragged.node.tempMass = 1000
                    dragged = null
                    $(canvas).unbind('mousemove', handler.dragged)
                    $(window).unbind('mouseup', handler.dropped)
                    _mouseP = null
                    return false
                }
            }
            // start listening
            $(canvas).mousedown(handler.clicked);
        }
    }
    return that
}
function resetAfterClick(){
    $('#nodelink').text("")
    $("#edgesTable > tbody").find("tr:gt(0)").remove();
    $("#edgesTable > tbody").find("tr:eq(0)").remove();
    $('#nodeename').text("")
    $('#nodeassay').text("")
    $('#assaylink').attr("href", "")


}

$(document).ready(function(){

    //arbor.ParticleSystem(repulsion, stiffness, friction, gravity, fps, dt, precision)
    var sys = arbor.ParticleSystem(2600, 6, 1.0, true, 55, 0.02, 0.4); // create the system with sensible repulsion/stiffness/friction
    sys.renderer = Renderer("#viewport"); // our newly created renderer will have its .init() method called shortly by sys...

    var projectId = $('#projectIdForStep').val()
    var graphInJSON = $.parseJSON($('#stepGraph').html());


            var connectedNodes = graphInJSON.connectedNodes;

            for (var i = 0; i < connectedNodes.length; i++)
            {
                var keyValues = connectedNodes[i].keyValues;
                sys.addNode(connectedNodes[i].id,
                    {name: keyValues.eid, name1: keyValues.stage, fixed: true,
                        link: keyValues.eid, assay: keyValues.assay,
                        ename: keyValues.ename});
            }


            var isolatedNodes = graphInJSON.isolatedNodes;

            for (var i = 0; i < isolatedNodes.length; i++) {
                var keyValues = isolatedNodes[i].keyValues;
                sys.addNode(isolatedNodes[i].id,
                    {alone:true, name: keyValues.eid, name1: keyValues.stage,
                        fixed: true, x: 10, y: 10,
                        link: keyValues.eid, assay: keyValues.assay,
                        ename: keyValues.ename});
            }


            var edges = graphInJSON.edges;
            for (var i = 0; i < edges.length; i++)
            {
                sys.addEdge(edges[i].from, edges[i].to,
                    {name: "", length:.75});
                //{name: graphInJSON.edges[i].label})
            }

});