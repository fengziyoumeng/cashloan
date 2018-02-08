import React from 'react';
import {Tabs} from 'antd';
// var ShowChannel = require('../FlowInfo/ShowChannelRegister/Index');
/*加载默认页*/
import Reflux from 'reflux';

var TabPane = Tabs.TabPane;
var Workbench = require('../Public/Workbench/Index');

var AppActions = require('./actions/AppActions');
var TabListStore = require('./stores/TabListStore');
 
var NavTabForChannel = React.createClass({
  mixins: [ 
    Reflux.listenTo(TabListStore, 'onMatch')
  ],
  onMatch(data) { 
        this.setState({
            activeId:data.activeId,
            tablist: data.tablist
        });
  },
  getInitialState() {
    return {
      tablist: [{'key': 'workbench', 'tabName': '欢迎',"tabId":'workbench',tabContent:"查看数据请点击左侧->注册数统计<-"}],
      activeId: "workbench",
    }
  }, 
  onChange(activeId) { 
    this.setState({activeId});
  },
  remove(tabId, e) {
    e.stopPropagation();
    AppActions.removeTab(tabId); 
  }, 
  render() { 
    return (
      <div className="Mytabs">
          <Tabs activeKey={this.state.activeId} onChange={this.onChange} destroyInactiveTabPane animation={null} contentStyle={{height:document.body.clientHeight-100,overflow: 'auto'}}>
                {
                  this.state.tablist.map((t, i)=> {
                    return  <TabPane key={t.tabId} tab={<div>{t.tabName} <i className="anticon anticon-cross-circle"  onClick={this.remove.bind(this,t.tabId)}></i></div>}>
                            {t.tabContent}
                        </TabPane>;
                  })
                }
          </Tabs>
      </div>);
  }
});

export default NavTabForChannel;
