package org.scada_lts.web.mvc.api.datasources;

import com.serotonin.mango.vo.dataSource.PointLocatorVO;
import com.serotonin.mango.vo.dataSource.virtual.*;

public class VirtualPointLocatorJson extends DataPointLocatorAbstract<VirtualPointLocatorVO> {

    int changeTypeId;
    AlternateBooleanChangeVO alternateBooleanChange;
    BrownianChangeVO brownianChange;
    IncrementAnalogChangeVO incrementAnalogChange;
    IncrementMultistateChangeVO incrementMultistateChange;
    NoChangeVO noChange;
    RandomAnalogChangeVO randomAnalogChange;
    RandomBooleanChangeVO randomBooleanChange;
    RandomMultistateChangeVO randomMultistateChange;
    AnalogAttractorChangeVO analogAttractorChange;

    public VirtualPointLocatorJson() {}

    public VirtualPointLocatorJson(VirtualPointLocatorVO pointLocatorVO) {
        this.setDataTypeId(pointLocatorVO.getDataTypeId());
        this.setSettable(pointLocatorVO.isSettable());
        this.changeTypeId = pointLocatorVO.getChangeTypeId();
        this.alternateBooleanChange = pointLocatorVO.getAlternateBooleanChange();
        this.brownianChange = pointLocatorVO.getBrownianChange();
        this.incrementAnalogChange = pointLocatorVO.getIncrementAnalogChange();
        this.incrementMultistateChange = pointLocatorVO.getIncrementMultistateChange();
        this.noChange = pointLocatorVO.getNoChange();
        this.randomAnalogChange = pointLocatorVO.getRandomAnalogChange();
        this.randomBooleanChange = pointLocatorVO.getRandomBooleanChange();
        this.randomMultistateChange = pointLocatorVO.getRandomMultistateChange();
        this.analogAttractorChange = pointLocatorVO.getAnalogAttractorChange();
    }



    public int getChangeTypeId() {
        return changeTypeId;
    }

    public void setChangeTypeId(int changeTypeId) {
        this.changeTypeId = changeTypeId;
    }

    public AlternateBooleanChangeVO getAlternateBooleanChange() {
        return alternateBooleanChange;
    }

    public void setAlternateBooleanChange(AlternateBooleanChangeVO alternateBooleanChange) {
        this.alternateBooleanChange = alternateBooleanChange;
    }

    public BrownianChangeVO getBrownianChange() {
        return brownianChange;
    }

    public void setBrownianChange(BrownianChangeVO brownianChange) {
        this.brownianChange = brownianChange;
    }

    public IncrementAnalogChangeVO getIncrementAnalogChange() {
        return incrementAnalogChange;
    }

    public void setIncrementAnalogChange(IncrementAnalogChangeVO incrementAnalogChange) {
        this.incrementAnalogChange = incrementAnalogChange;
    }

    public IncrementMultistateChangeVO getIncrementMultistateChange() {
        return incrementMultistateChange;
    }

    public void setIncrementMultistateChange(IncrementMultistateChangeVO incrementMultistateChange) {
        this.incrementMultistateChange = incrementMultistateChange;
    }

    public NoChangeVO getNoChange() {
        return noChange;
    }

    public void setNoChange(NoChangeVO noChange) {
        this.noChange = noChange;
    }

    public RandomAnalogChangeVO getRandomAnalogChange() {
        return randomAnalogChange;
    }

    public void setRandomAnalogChange(RandomAnalogChangeVO randomAnalogChange) {
        this.randomAnalogChange = randomAnalogChange;
    }

    public RandomBooleanChangeVO getRandomBooleanChange() {
        return randomBooleanChange;
    }

    public void setRandomBooleanChange(RandomBooleanChangeVO randomBooleanChange) {
        this.randomBooleanChange = randomBooleanChange;
    }

    public RandomMultistateChangeVO getRandomMultistateChange() {
        return randomMultistateChange;
    }

    public void setRandomMultistateChange(RandomMultistateChangeVO randomMultistateChange) {
        this.randomMultistateChange = randomMultistateChange;
    }

    public AnalogAttractorChangeVO getAnalogAttractorChange() {
        return analogAttractorChange;
    }

    public void setAnalogAttractorChange(AnalogAttractorChangeVO analogAttractorChange) {
        this.analogAttractorChange = analogAttractorChange;
    }

    @Override
    public VirtualPointLocatorVO parsePointLocatorData() {
        VirtualPointLocatorVO  plVO = new VirtualPointLocatorVO();
        plVO.setDataTypeId(this.getDataTypeId());
        plVO.setChangeTypeId(this.getChangeTypeId());
        plVO.setAlternateBooleanChange(this.getAlternateBooleanChange());
        plVO.setRandomBooleanChange(this.getRandomBooleanChange());
        plVO.setIncrementMultistateChange(this.getIncrementMultistateChange());
        plVO.setRandomMultistateChange(this.getRandomMultistateChange());
        plVO.setNoChange(this.getNoChange());
        plVO.setBrownianChange(this.getBrownianChange());
        plVO.setIncrementAnalogChange(this.getIncrementAnalogChange());
        plVO.setRandomAnalogChange(this.getRandomAnalogChange());
        plVO.setAnalogAttractorChange(this.getAnalogAttractorChange());
        return plVO;
    }
}
