package com.somesame.framework.widget.refresh.resistance;


import com.somesame.framework.widget.refresh.IResistance;

/**
 */
public class DampingHalf implements IResistance {

    @Override
    public IResistance clone_() {
        DampingHalf clone_ = new DampingHalf();
        return clone_;
    }
    @Override
    public int getOffSetYMapValue(int headerHeight, int offset) {
        return offset/2;
    }

}
