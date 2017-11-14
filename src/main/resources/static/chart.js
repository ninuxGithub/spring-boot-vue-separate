/**
 * nodes :
 * 		name：节点的title，
 * 		group:围绕中心的层数，一个组合同一个颜色
 * 		index:区分的标记
 * 
 * links:
 * 		source:源
 * 		target:链接的目的地
 */
$(document).ready(function () {
	var renderId ="#main_2";
	
    var jsonStr = {
        "nodes": [
            {"name": "中国", "group": 1, "index": 0},
            {"name": "内蒙古", "group": 2, "index": 1},
            {"name": "猫咪", "group": 3, "index": 2},
            {"name": "四川", "group": 2, "index": 3},
            {"name": "棕熊", "group": 2, "index": 4},
            {"name": "臭豆腐", "group": 3, "index": 5},
            {"name": "小猪猪", "group": 2, "index": 6},
            {"name": "湖南", "group": 2, "index": 7},
            {"name": "大熊猫", "group": 3, "index": 8},
            {"name": "北京", "group": 2, "index": 9},
            {"name": "雾霾", "group": 3, "index": 10}
        ],
        "links": [
        	{"source": 0, "target": 1},
            {"source": 1, "target": 2},
            {"source": 0, "target": 3},
            {"source": 3, "target": 8},
            {"source": 0, "target": 4},
            {"source": 0, "target": 6},
            {"source": 0, "target": 7},
            {"source": 7, "target": 5},
            {"source": 0, "target": 9},
            {"source": 9, "target": 10}
        ]
    };
    
    drawChart(renderId,jsonStr);/*在$(“#main_2”)作用域内画图*/

});

/**
 * 
 * @param renderId 渲染的ID
 * @param graph  数据
 * @returns
 */
function drawChart(renderId, graph) {
	var index = 0;
	var width = 660, height = 580;
	var color = ["#FF8000", "#9393FF", "#0080FF"];
	var force = d3.layout.force()		/*layout将json格式转化为力学图可用的格式*/
	.linkDistance(20)			/*指定结点连接线的距离，默认为20*/
	.charge(-300)				/*顶点的电荷数。该参数决定是排斥还是吸引，数值越小越互相排斥*/
	.size([width, height]);		/*作用域*/
	var svg;
	var dd = $('<div class="d3strench" id="d3strench" style="border: 1px green solid;"></div>');
	$(renderId).append(dd);
	/*D3采用SVG来更加生动展现数据，此处设置svg的基本样式*/
	svg = d3.select(".d3strench").append("svg")
	.attr("width", width)
	.attr("height", height)
	.attr('border', 'red')
	.style({
		'margin': '0 auto',
		'display': 'block'
	});
	var nodes = graph.nodes.slice(), /*nodes() 里传入顶点的数组*/ links = [], bilinks = [];
	
	
	/*将数据组装成source-->target的形式*/
	graph.links.forEach(function (link) {
		var s = nodes[link.source],
		t = nodes[link.target],
		i = {}; // 中间节点
		nodes.push(i);
		links.push({source: s, target: i}, {source: i, target: t});
		bilinks.push([s, i, t]);
	});
	force.nodes(nodes).links(links).start();
	
	/*svg的path标签被称为”可以组成任何形状的形状”,所以此处用path标签来绘制线条*/
	var link = svg.selectAll(".link")//线条
	.data(bilinks)
	.enter().append("path")
	.attr("class", "link")
	.attr("stroke", function (d) {
		return color[d[0].group - 1];
	}).attr("stroke-width", 1)
	
	/*接下来是数据的渲染*/
	var node = svg.selectAll(".node")
	.data(graph.nodes)
	.enter().append("g")
	.attr("class", "node")
	.attr("group", function (d) {
		return d.group;
	}).call(force.drag);
	
	node.append("circle")
	.attr("r", 8)
	.style("fill", function (d) {
		return color[d.group - 1];
	})
	.style("stroke", function (d) {
		return color[d.group - 1];
	}).style("stroke-width", "8") //圆外面的轮廓线
	.style("stroke-opacity", "0.6"); //圆外面的轮廓线的透明度
	
	node.filter(function (d) {
		return d.group !== 0;
	}).append("text")
	.attr("font-family", "微软雅黑")
	.attr("text-anchor", "middle")
	.attr("dy", function () {   //dy表示文字的偏移量
		return "0em";
	})
	.attr('x', function (d) {
		d3.select(this).append('tspan')//添加文字
		.text(function () {
			return d.name;
		});
		d3.selectAll(".node[group='3'] text")//设置圆圈的样式以及半径
		.selectAll("tspan")
		.attr("fill", "#000");
		d3.selectAll(".node[group='1'] circle")
		.attr('r', 40)
		.style('cursor', 'pointer');
		d3.selectAll(".node[group='2'] circle")
		.attr('r', 25);
		d3.selectAll(".node[group='3'] circle")
		.attr('r', 30);
	});
	
	
	//为每个节点设置title（类似于html标签的title属性）
	node.append("title").text(function (d) {return d.name;});
	
	/*拖拽事件*/
	force.on("tick", function () {
		link.attr("d", function (d) {//设置线条的偏移以及路径
			var dx = d[2].x - d[0].x, dy = d[2].y - d[0].y, dr = Math.sqrt(dx * dx + dy * dy);
			/*下面表示位置的菜蔬中， M（表示画笔落下的位置）, A（画椭圆）是大写的，表示绝对位置。当使用相对位置时，要小写*/
			return "M" + d[0].x + "," + d[0].y + "A" + dr + "," + dr + " 0 0,0 " + d[2].x + "," + d[2].y;
		}).attr("fill", "transparent");
		node.attr("transform", function (d) {//circle节点的偏移量
			return "translate(" + d.x + "," + d.y + ")";
		});
	});
	force.stop();
	force.start();
}