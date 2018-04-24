package com.color.card.entity;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yqy
 * @date on 2018/4/12
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public class BloodEntity {
    private String value;

    private RGBentity rgBentity;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public RGBentity getRgBentity() {
        return rgBentity;
    }

    public void setRgBentity(RGBentity rgBentity) {
        this.rgBentity = rgBentity;
    }


    public static List<BloodEntity> creatBloodEntityList() {
        List<BloodEntity> bloodEntities = new ArrayList<>();
        // 0 mmol/L 的数值加入
        BloodEntity bloodEntity = new BloodEntity();
        bloodEntity.setValue("0.0");
        bloodEntity.setRgBentity(new RGBentity(198, 249, 230));
        bloodEntities.add(bloodEntity);

        //0.5 mmol/L 的数值加入
        BloodEntity bloodEntity1 = new BloodEntity();
        bloodEntity1.setValue("0.5");
        bloodEntity1.setRgBentity(new RGBentity(171, 217, 191));
        bloodEntities.add(bloodEntity1);

        //1.0 mmol/L 的数值加入
        BloodEntity bloodEntity2 = new BloodEntity();
        bloodEntity2.setValue("1.0");
        bloodEntity2.setRgBentity(new RGBentity(174, 236, 187));
        bloodEntities.add(bloodEntity2);


        //1.5 mmol/L 的数值加入
        BloodEntity bloodEntity3 = new BloodEntity();
        bloodEntity3.setValue("1.5");
        bloodEntity3.setRgBentity(new RGBentity(150, 212, 152));
        bloodEntities.add(bloodEntity3);


        //2.0 mmol/L 的数值加入
        BloodEntity bloodEntity4 = new BloodEntity();
        bloodEntity4.setValue("2.0");
        bloodEntity4.setRgBentity(new RGBentity(202, 241, 164));
        bloodEntities.add(bloodEntity4);


        //2.5 mmol/L 的数值加入
        BloodEntity bloodEntity5 = new BloodEntity();
        bloodEntity5.setValue("2.5");
        bloodEntity5.setRgBentity(new RGBentity(210, 242, 133));
        bloodEntities.add(bloodEntity5);


        //3.0 mmol/L 的数值加入
        BloodEntity bloodEntity6 = new BloodEntity();
        bloodEntity6.setValue("3.0");
        bloodEntity6.setRgBentity(new RGBentity(207, 243, 119));
        bloodEntities.add(bloodEntity6);


        //3.5 mmol/L 的数值加入
        BloodEntity bloodEntity7 = new BloodEntity();
        bloodEntity7.setValue("3.5");
        bloodEntity7.setRgBentity(new RGBentity(209, 231, 121));
        bloodEntities.add(bloodEntity7);


        //4.0 mmol/L 的数值加入
        BloodEntity bloodEntity8 = new BloodEntity();
        bloodEntity8.setValue("4.0");
        bloodEntity8.setRgBentity(new RGBentity(202, 224, 112));
        bloodEntities.add(bloodEntity8);


        //4.5 mmol/L 的数值加入
        BloodEntity bloodEntity9 = new BloodEntity();
        bloodEntity9.setValue("4.5");
        bloodEntity9.setRgBentity(new RGBentity(185, 204, 94));
        bloodEntities.add(bloodEntity9);


        //5.0 mmol/L 的数值加入
        BloodEntity bloodEntity10 = new BloodEntity();
        bloodEntity10.setValue("5.0");
        bloodEntity10.setRgBentity(new RGBentity(233, 242, 112));
        bloodEntities.add(bloodEntity10);


        //5.5 mmol/L 的数值加入
        BloodEntity bloodEntity11 = new BloodEntity();
        bloodEntity11.setValue("5.5");
        bloodEntity11.setRgBentity(new RGBentity(227, 239, 113));
        bloodEntities.add(bloodEntity11);


        //6.0 mmol/L 的数值加入
        BloodEntity bloodEntity12 = new BloodEntity();
        bloodEntity12.setValue("6.0");
        bloodEntity12.setRgBentity(new RGBentity(188, 207, 75));
        bloodEntities.add(bloodEntity12);


        //6.5 mmol/L 的数值加入
        BloodEntity bloodEntity13 = new BloodEntity();
        bloodEntity13.setValue("6.5");
        bloodEntity13.setRgBentity(new RGBentity(225, 236, 101));
        bloodEntities.add(bloodEntity13);


        //7.0 mmol/L 的数值加入
        BloodEntity bloodEntity14 = new BloodEntity();
        bloodEntity14.setValue("7.0");
        bloodEntity14.setRgBentity(new RGBentity(236, 243, 115));
        bloodEntities.add(bloodEntity14);


        //7.5 mmol/L 的数值加入
        BloodEntity bloodEntity15 = new BloodEntity();
        bloodEntity15.setValue("7.5");
        bloodEntity15.setRgBentity(new RGBentity(211, 216, 93));
        bloodEntities.add(bloodEntity15);


        //8.0 mmol/L 的数值加入
        BloodEntity bloodEntity16 = new BloodEntity();
        bloodEntity16.setValue("8.0");
        bloodEntity16.setRgBentity(new RGBentity(231, 232, 103));
        bloodEntities.add(bloodEntity16);


        //9.0 mmol/L 的数值加入
        BloodEntity bloodEntity17 = new BloodEntity();
        bloodEntity17.setValue("9.0");
        bloodEntity17.setRgBentity(new RGBentity(197, 201, 68));
        bloodEntities.add(bloodEntity17);


        //10.0 mmol/L 的数值加入
        BloodEntity bloodEntity18 = new BloodEntity();
        bloodEntity18.setValue("10.0");
        bloodEntity18.setRgBentity(new RGBentity(216, 205, 79));
        bloodEntities.add(bloodEntity18);


        //11.0 mmol/L 的数值加入
        BloodEntity bloodEntity19 = new BloodEntity();
        bloodEntity19.setValue("11.0");
        bloodEntity19.setRgBentity(new RGBentity(212, 206, 85));
        bloodEntities.add(bloodEntity19);


        //12.0 mmol/L 的数值加入
        BloodEntity bloodEntity20 = new BloodEntity();
        bloodEntity20.setValue("12.0");
        bloodEntity20.setRgBentity(new RGBentity(216, 199, 83));
        bloodEntities.add(bloodEntity20);


        //13.0 mmol/L 的数值加入
        BloodEntity bloodEntity21 = new BloodEntity();
        bloodEntity21.setValue("13.0");
        bloodEntity21.setRgBentity(new RGBentity(209, 183, 82));
        bloodEntities.add(bloodEntity21);


        //14.0 mmol/L 的数值加入
        BloodEntity bloodEntity22 = new BloodEntity();
        bloodEntity22.setValue("14.0");
        bloodEntity22.setRgBentity(new RGBentity(197, 172, 71));
        bloodEntities.add(bloodEntity22);


        //16.0 mmol/L 的数值加入
        BloodEntity bloodEntity23 = new BloodEntity();
        bloodEntity23.setValue("16.0");
        bloodEntity23.setRgBentity(new RGBentity(205, 164, 68));
        bloodEntities.add(bloodEntity23);

        //18.0 mmol/L 的数值加入
        BloodEntity bloodEntity24 = new BloodEntity();
        bloodEntity24.setValue("18.0");
        bloodEntity24.setRgBentity(new RGBentity(200, 159, 69));
        bloodEntities.add(bloodEntity24);


        //20.0 mmol/L 的数值加入
        BloodEntity bloodEntity25 = new BloodEntity();
        bloodEntity25.setValue("20.0");
        bloodEntity25.setRgBentity(new RGBentity(221, 178, 81));
        bloodEntities.add(bloodEntity25);


        //22.0 mmol/L 的数值加入
        BloodEntity bloodEntity26 = new BloodEntity();
        bloodEntity26.setValue("22.0");
        bloodEntity26.setRgBentity(new RGBentity(205, 154, 72));
        bloodEntities.add(bloodEntity26);


        //24.0 mmol/L 的数值加入
        BloodEntity bloodEntity27 = new BloodEntity();
        bloodEntity27.setValue("24.0");
        bloodEntity27.setRgBentity(new RGBentity(148, 94, 35));
        bloodEntities.add(bloodEntity27);


        //26.0 mmol/L 的数值加入
        BloodEntity bloodEntity28 = new BloodEntity();
        bloodEntity28.setValue("26.0");
        bloodEntity28.setRgBentity(new RGBentity(176, 124, 54));
        bloodEntities.add(bloodEntity28);


        //28.0 mmol/L 的数值加入
        BloodEntity bloodEntity29 = new BloodEntity();
        bloodEntity29.setValue("28.0");
        bloodEntity29.setRgBentity(new RGBentity(188, 142, 53));
        bloodEntities.add(bloodEntity29);


        //30.0 mmol/L 的数值加入
        BloodEntity bloodEntity30 = new BloodEntity();
        bloodEntity30.setValue("30.0");
        bloodEntity30.setRgBentity(new RGBentity(196, 143, 59));
        bloodEntities.add(bloodEntity30);


        //35.0 mmol/L 的数值加入
        BloodEntity bloodEntity31 = new BloodEntity();
        bloodEntity31.setValue("35.0");
        bloodEntity31.setRgBentity(new RGBentity(188, 132, 56));
        bloodEntities.add(bloodEntity31);

        //35.0 mmol/L 的数值加入
        BloodEntity bloodEntity32 = new BloodEntity();
        bloodEntity32.setValue("40.0");
        bloodEntity32.setRgBentity(new RGBentity(183, 129, 62));
        bloodEntities.add(bloodEntity32);

        return bloodEntities;
    }
}
