import React from 'react';
import {Button, Form, Input, Select,Message,DatePicker } from 'antd';
const createForm = Form.create;
const FormItem = Form.Item;
const Option = Select.Option;

let SeachForm = React.createClass({
    getInitialState() {
        return {
            roleList: []
        }
    },
    render() {

        const {getFieldProps} = this.props.form;

        return (
            <Form inline>
             <FormItem label="时期:">
                  <DatePicker> </DatePicker>
             </FormItem>
             <FormItem label="至:">
                  <DatePicker></DatePicker>
             </FormItem>
             <FormItem label="来源:">
                    < Select style={{ width: 100 }} placeholder='请选择...'>
                        <Option value="全部">全部</Option>
                        <Option value="x5-1zdxT">x5-1zdxT</Option>
                        <Option value="H5-xyqd">H5-xyqd</Option>
                        <Option value="WAP-guangdt37">WAP-guangdt37</Option>
                        <Option value="WAP-lemo4">WAP-lemo4</Option>
                    </Select>
             </FormItem>
             
             <FormItem><Button type="primary" onClick={()=>{}}>过滤</Button></FormItem>
            </Form>
        );
    }
});

SeachForm = createForm()(SeachForm);
export default SeachForm;