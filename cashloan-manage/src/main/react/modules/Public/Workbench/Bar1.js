import React from 'react';
var echarts = require('echarts');
require("echarts/theme/macarons.js");
export default React.createClass({
	getInitialState() {
        return {
            menuData: [],
            assetsData: {},
            investmentData: {},
            loading: false,
            data: [],
			item: [],
            first: true,
			max:20000
        }
    },
	fetch(data) {
        var me = this;
		var data1 = [];
		var item1 = [];
		var item2 = [];
		var max = 10000
		for (let item in data.countFifteenDaysRegister) {
			item1.push(item.substring(5));
			item2.push(item);
		}
		for (var i = 0; i < item1.length - 1; i++) {
			for (var j = i + 1; j < item1.length; j++) {
				if (item1[i] > item1[j]) {
					var z = item1[i];
					item1[i] = item1[j];
					item1[j] = z;
					var q = item2[i];
					item2[i] = item2[j];
					item2[j] = q;
				}
			}
		}
		for (var i = 0; i < item1.length; i++) {
            data1.push(data.countFifteenDaysRegister[item2[i]]);
		}
		me.setState({
			data: data1,
			item: item1,
            first: false
		},() => {
            me.drawBar();
        });
    },
	drawBar() {
		var me = this;
		var bar = echarts.init(document.getElementById('bar'),'macarons');
		var option = {
			title: {
				text: '15天注册量(每20分钟更新)',
				x: 'center',
				y: 10,
				textStyle: {
					color: '#666',
					fontWeight: 'normal'
				}
			},
			tooltip: {
				trigger: 'axis',
				formatter: '{a} <br/>{b}: {c}笔',
				axisPointer: {            // 坐标轴指示器，坐标轴触发有效
					type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				}
			},
			barWidth: '10',
			legend: {
				orient: 'horizontal',
				x: 'right',
				y: 20,
				itemGap: 20,
				data: ['每天注册量']
			},
			grid: {
				left: '3%',
				right: '4%',
				bottom: '3%',
				containLabel: true
			},
			xAxis: [
				{
					type: 'category',
					data: me.state.item
				}
			],
			yAxis: {
				type: 'value',
				splitLine: { show: false },
                max: me.state.max % 2 == 0 ? me.state.max : me.state.max + 1,
			},
			series: [
				{
					name: '每天注册量',
					type: 'bar',
					data: me.state.data
				},
			]
		};
		bar.setOption(option);
	},
	componentWillReceiveProps(nextProps) {
		if(nextProps.data.constructor == Object && this.state.first){
            this.fetch(nextProps.data);
        }
        
    },
	render() {
		return <div id="bar" style={{ height: '300px', width: '1500px', margin: '0 auto'  }}></div>
	}
});