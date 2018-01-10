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
             <FormItem label="日期:">
                  <DatePicker> </DatePicker>
             </FormItem>
             <FormItem label="至:">
                  <DatePicker></DatePicker>
             </FormItem>
             
             <FormItem><Button type="primary" onClick={()=>{}}>过滤</Button></FormItem>
            </Form>
        );
    }
});

SeachForm = createForm()(SeachForm);
export default SeachForm;