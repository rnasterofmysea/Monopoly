
import java.awt.Label;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author scw
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    int diceResult; // 주사위 실행 결과

    Location[] locationList = new Location[24];

    // DB
    LocationDB LDB = new LocationDB();
    String sql = "select * from location_table";
    
    Player player1 = new Player(1, 20, 800); // 1P 생성
    Player player2 = new Player(2, 20, 800); // 2P 생성
    int flag = 1; // 턴 확인 flag
    int turn = 20; // = 턴수 * 2
    private String text;
    
    private JLabel[] locationlblList;
    
    public JLabel[] getLocationlblList() {
        return locationlblList;
    }
    
    public void setLocationlblList(JLabel[] locationlblList) {
        this.locationlblList = locationlblList;
    }
    private JLabel[] playerlblList;
    
    public JLabel[] getPlayerlblList() {
        return playerlblList;
    }
    
    public void setPlayerlblList(JLabel[] playerlblList) {
        this.playerlblList = playerlblList;
    }
    
    private Player[] playerList;
    
    public Player[] getPlayerList() {
        return playerList;
    }
    
    public void setPlayerList(Player[] playerList) {
        this.playerList = playerList;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public MainFrame() {
        initComponents();
        // DB
        JLabel[] locIdx = {location1, location2, location3, location4, location5, location6, location7, location8, location9, location10,
            location11, location12, location13, location14, location15, location16, location17, location18, location19, location20,
            location21, location22, location23, location24};
        setLocationlblList(locIdx);
        
        JLabel[] playeridx = {lblPlayer1, lblPlayer2};
        setPlayerlblList(playeridx);
        
        Player[] playerUser = {player1, player2};
        setPlayerList(playerUser);

        //location1의 시작 좌표 값
        int x = 20;
        int y = 800;
        
        for (int i = 0; i < playerlblList.length; i++) {
            playerlblList[i].setLocation(playerList[i].getX(), playerList[i].getY()); // 간편화
        }

        // location x / y값 할당
        for (int i = 0; i < 24; i++) {
            if (i < 6) {
                Location location = new Location(x, y);
                locationList[i] = location;
                x += 130;
            } else if (i < 12) {
                Location location = new Location(x, y);
                locationList[i] = location;
                y -= 130;
            } else if (i < 18) {
                Location location = new Location(x, y);
                locationList[i] = location;
                x -= 130;
            } else {
                Location location = new Location(x, y);
                locationList[i] = location;
                y += 130;
            }
        }
        try {
            LDB.dbOpen();
            getDBData(sql);
            LDB.dbClose();
        } catch (Exception e) {
            System.out.println("SQLException : " + e.getMessage());
        }
        lblerror.setText(Integer.toString(locationList[1].getX()));
        lblerror2.setText(Integer.toString(locationList[23].getLoc_ground()));

        // location 위치 배정
        for (int i = 0; i < 24; i++) {
            locIdx[i].setLocation(locationList[i].getX(), locationList[i].getY());
        }

// 게임판 세팅
//
//        while (turn > 0) {
//            // 버튼이 클릭되면 
//            switch (flag) {
//                case 1: { //1P
//                    int judge = player1.movement(diceResult, locationList);
//                    if (judge == 0) {
//                        // 구매 여부
//                    } else if (judge == player1.getPlayerNum()) {
//                        // 재구매 여부
//                    } else {
//                        // 통행료 지불
//                    }
//                    flag = 1;
//                    turn--;
//                    break;
//                }
//                case 2: {//2
//                    int judge = player2.movement(diceResult, locationList);
//                    if (judge == 0) {
//                        // 구매 여부
//                    } else if (judge == player1.getPlayerNum()) {
//                        // 재구매 여부
//                    } else {
//                        // 통행료 지불
//                    }
//                    flag = 1;
//                    turn--;
//                    break;
//                }
//            }
//        }
    }
    
    public void getDBData(String sql) {
        int idx = 0;
        
        try {
            LDB.rs = LDB.stmt.executeQuery(sql);
            while (LDB.rs.next()) {
                locationList[idx].setLoc_ground(LDB.rs.getInt("loc_ground"));
                locationList[idx].setLoc_vila(LDB.rs.getInt("loc_vila"));
                locationList[idx].setLoc_building(LDB.rs.getInt("loc_building"));
                locationList[idx].setLoc_hotel(LDB.rs.getInt("loc_hotel"));
                locationList[idx].setLoc_name(LDB.rs.getString("loc_name"));
                getLocationlblList()[idx].setText(LDB.rs.getString("loc_name"));
                idx++;
            }
            LDB.rs.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buyFrame = new javax.swing.JFrame();
        lblBuyTitle = new javax.swing.JLabel();
        btnBuyConfirm = new javax.swing.JButton();
        chkLoc_ground = new javax.swing.JCheckBox();
        chkLoc_vila = new javax.swing.JCheckBox();
        chkLoc_building = new javax.swing.JCheckBox();
        chkLoc_hotel = new javax.swing.JCheckBox();
        lblBuy = new javax.swing.JLabel();
        lblSell = new javax.swing.JLabel();
        lblCost = new javax.swing.JLabel();
        buyGroundVal = new javax.swing.JLabel();
        sellGroundVal = new javax.swing.JLabel();
        costGroundVal = new javax.swing.JLabel();
        costVilaVal = new javax.swing.JLabel();
        sellVilaVal = new javax.swing.JLabel();
        buyVilaVal = new javax.swing.JLabel();
        costBuildingVal = new javax.swing.JLabel();
        sellBuildingVal = new javax.swing.JLabel();
        buyBuildingVal = new javax.swing.JLabel();
        costHotelVal = new javax.swing.JLabel();
        sellHotelVal = new javax.swing.JLabel();
        buyHotelVal = new javax.swing.JLabel();
        btnBuyCancel = new javax.swing.JButton();
        jFrame2 = new javax.swing.JFrame();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        location20 = new javax.swing.JLabel();
        location14 = new javax.swing.JLabel();
        location6 = new javax.swing.JLabel();
        location1 = new javax.swing.JLabel();
        location22 = new javax.swing.JLabel();
        location21 = new javax.swing.JLabel();
        location23 = new javax.swing.JLabel();
        location24 = new javax.swing.JLabel();
        location9 = new javax.swing.JLabel();
        location7 = new javax.swing.JLabel();
        location4 = new javax.swing.JLabel();
        location5 = new javax.swing.JLabel();
        location2 = new javax.swing.JLabel();
        location3 = new javax.swing.JLabel();
        location8 = new javax.swing.JLabel();
        location12 = new javax.swing.JLabel();
        location10 = new javax.swing.JLabel();
        location11 = new javax.swing.JLabel();
        location13 = new javax.swing.JLabel();
        location16 = new javax.swing.JLabel();
        location15 = new javax.swing.JLabel();
        location19 = new javax.swing.JLabel();
        location17 = new javax.swing.JLabel();
        location18 = new javax.swing.JLabel();
        Dice = new javax.swing.JButton();
        lblPlayer1 = new javax.swing.JLabel();
        lblPlayer2 = new javax.swing.JLabel();
        diceNum = new javax.swing.JLabel();
        form2 = new javax.swing.JButton();
        lblerror = new javax.swing.JLabel();
        lblerror2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();

        buyFrame.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                buyFrameComponentShown(evt);
            }
        });

        lblBuyTitle.setText("해당 지역 이름");

        btnBuyConfirm.setText("구매하기");
        btnBuyConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuyConfirmActionPerformed(evt);
            }
        });

        chkLoc_ground.setText("토지");

        chkLoc_vila.setText("빌라");

        chkLoc_building.setText("빌딩");

        chkLoc_hotel.setText("호텔");

        lblBuy.setText("구매가");

        lblSell.setText("판매가");

        lblCost.setText("통행료");

        buyGroundVal.setText("jLabel6");

        sellGroundVal.setText("jLabel6");

        costGroundVal.setText("jLabel6");

        costVilaVal.setText("jLabel6");

        sellVilaVal.setText("jLabel6");

        buyVilaVal.setText("jLabel6");

        costBuildingVal.setText("jLabel6");

        sellBuildingVal.setText("jLabel6");

        buyBuildingVal.setText("jLabel6");

        costHotelVal.setText("jLabel6");

        sellHotelVal.setText("jLabel6");

        buyHotelVal.setText("jLabel6");

        btnBuyCancel.setText("취소하기");
        btnBuyCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuyCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buyFrameLayout = new javax.swing.GroupLayout(buyFrame.getContentPane());
        buyFrame.getContentPane().setLayout(buyFrameLayout);
        buyFrameLayout.setHorizontalGroup(
            buyFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buyFrameLayout.createSequentialGroup()
                .addGroup(buyFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(buyFrameLayout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(btnBuyConfirm)
                        .addGap(45, 45, 45)
                        .addComponent(btnBuyCancel))
                    .addGroup(buyFrameLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(buyFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSell)
                            .addComponent(lblBuy)
                            .addComponent(lblCost))
                        .addGap(42, 42, 42)
                        .addGroup(buyFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkLoc_ground, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buyFrameLayout.createSequentialGroup()
                                .addGroup(buyFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(buyGroundVal)
                                    .addComponent(sellGroundVal)
                                    .addComponent(costGroundVal))
                                .addGap(11, 11, 11)))
                        .addGap(22, 22, 22)
                        .addGroup(buyFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buyVilaVal)
                            .addComponent(sellVilaVal)
                            .addComponent(costVilaVal)
                            .addComponent(chkLoc_vila))
                        .addGap(18, 18, 18)
                        .addGroup(buyFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkLoc_building)
                            .addComponent(buyBuildingVal)
                            .addComponent(sellBuildingVal)
                            .addComponent(costBuildingVal))
                        .addGap(18, 18, 18)
                        .addGroup(buyFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkLoc_hotel)
                            .addComponent(buyHotelVal)
                            .addComponent(sellHotelVal)
                            .addComponent(costHotelVal)))
                    .addGroup(buyFrameLayout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(lblBuyTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        buyFrameLayout.setVerticalGroup(
            buyFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buyFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblBuyTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(buyFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkLoc_ground)
                    .addComponent(chkLoc_vila)
                    .addComponent(chkLoc_building)
                    .addComponent(chkLoc_hotel))
                .addGap(24, 24, 24)
                .addGroup(buyFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(buyFrameLayout.createSequentialGroup()
                        .addComponent(lblBuy)
                        .addGap(18, 18, 18)
                        .addComponent(lblSell)
                        .addGap(18, 18, 18)
                        .addComponent(lblCost))
                    .addGroup(buyFrameLayout.createSequentialGroup()
                        .addComponent(buyVilaVal)
                        .addGap(18, 18, 18)
                        .addComponent(sellVilaVal)
                        .addGap(18, 18, 18)
                        .addComponent(costVilaVal))
                    .addGroup(buyFrameLayout.createSequentialGroup()
                        .addComponent(buyBuildingVal)
                        .addGap(18, 18, 18)
                        .addComponent(sellBuildingVal)
                        .addGap(18, 18, 18)
                        .addComponent(costBuildingVal))
                    .addGroup(buyFrameLayout.createSequentialGroup()
                        .addComponent(buyHotelVal)
                        .addGap(18, 18, 18)
                        .addComponent(sellHotelVal)
                        .addGap(18, 18, 18)
                        .addComponent(costHotelVal))
                    .addGroup(buyFrameLayout.createSequentialGroup()
                        .addComponent(buyGroundVal)
                        .addGap(18, 18, 18)
                        .addComponent(sellGroundVal)
                        .addGap(18, 18, 18)
                        .addComponent(costGroundVal)))
                .addGap(18, 18, 18)
                .addGroup(buyFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBuyConfirm)
                    .addComponent(btnBuyCancel))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jLabel1.setText("지역 정보");

        jLabel2.setText("총 가격");

        jLabel4.setText("구매할 것이냐");

        jButton1.setText("구매하기");

        jButton2.setText("통행료 지불");

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame2Layout.createSequentialGroup()
                .addGroup(jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jFrame2Layout.createSequentialGroup()
                        .addGap(183, 183, 183)
                        .addComponent(jLabel1))
                    .addGroup(jFrame2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2))
                    .addGroup(jFrame2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4))
                    .addGroup(jFrame2Layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(jButton1)
                        .addGap(42, 42, 42)
                        .addComponent(jButton2)))
                .addContainerGap(121, Short.MAX_VALUE))
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addGap(39, 39, 39)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 168, Short.MAX_VALUE)
                .addGroup(jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(48, 48, 48))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(null);

        location20.setText("19");
        location20.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location20);
        location20.setBounds(20, 150, 130, 130);

        location14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location14.setText("13");
        location14.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        location14.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location14);
        location14.setBounds(670, 20, 130, 130);

        location6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location6.setText("5");
        location6.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        location6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location6);
        location6.setBounds(670, 800, 130, 130);

        location1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location1.setText("시작1");
        location1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        location1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location1);
        location1.setBounds(20, 800, 130, 130);

        location22.setText("21");
        location22.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location22);
        location22.setBounds(20, 410, 130, 130);

        location21.setText("20");
        location21.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location21);
        location21.setBounds(20, 280, 130, 130);

        location23.setText("22");
        location23.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location23);
        location23.setBounds(20, 540, 130, 130);

        location24.setText("23");
        location24.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location24);
        location24.setBounds(20, 670, 130, 130);

        location9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        location9.setText("8");
        location9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location9);
        location9.setBounds(800, 540, 130, 130);

        location7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location7.setText("6");
        location7.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        location7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location7);
        location7.setBounds(800, 800, 130, 130);

        location4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location4.setText("3");
        location4.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        location4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location4);
        location4.setBounds(410, 800, 130, 130);

        location5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location5.setText("4");
        location5.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        location5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location5);
        location5.setBounds(540, 800, 130, 130);

        location2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location2.setText("1");
        location2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        location2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location2);
        location2.setBounds(150, 800, 130, 130);

        location3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location3.setText("2");
        location3.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        location3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location3);
        location3.setBounds(280, 800, 130, 130);

        location8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        location8.setText("7");
        location8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location8);
        location8.setBounds(800, 670, 130, 130);

        location12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        location12.setText("11");
        location12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location12);
        location12.setBounds(800, 150, 130, 130);

        location10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        location10.setText("9");
        location10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location10);
        location10.setBounds(800, 410, 130, 130);

        location11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        location11.setText("10");
        location11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location11);
        location11.setBounds(800, 280, 130, 130);

        location13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location13.setText("12");
        location13.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        location13.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location13);
        location13.setBounds(800, 20, 130, 130);

        location16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location16.setText("15");
        location16.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        location16.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location16);
        location16.setBounds(410, 20, 130, 130);

        location15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location15.setText("14");
        location15.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        location15.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location15);
        location15.setBounds(540, 20, 130, 130);

        location19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location19.setText("18");
        location19.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        location19.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location19);
        location19.setBounds(20, 20, 130, 130);

        location17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location17.setText("16");
        location17.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        location17.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location17);
        location17.setBounds(280, 20, 130, 130);

        location18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location18.setText("17");
        location18.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        location18.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location18);
        location18.setBounds(150, 20, 130, 130);

        Dice.setText("주사위");
        Dice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DiceMouseClicked(evt);
            }
        });
        jPanel1.add(Dice);
        Dice.setBounds(410, 640, 87, 27);

        lblPlayer1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/playerImg1.png"))); // NOI18N
        jPanel1.add(lblPlayer1);
        lblPlayer1.setBounds(210, 690, 30, 20);

        lblPlayer2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/playerImg2.png"))); // NOI18N
        jPanel1.add(lblPlayer2);
        lblPlayer2.setBounds(250, 690, 30, 20);

        diceNum.setText("주사위 : ");
        jPanel1.add(diceNum);
        diceNum.setBounds(410, 450, 80, 40);

        form2.setText("jButton1");
        form2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                form2ActionPerformed(evt);
            }
        });
        jPanel1.add(form2);
        form2.setBounds(260, 270, 87, 27);

        lblerror.setText("jLabel1");
        jPanel1.add(lblerror);
        lblerror.setBounds(190, 210, 46, 18);

        lblerror2.setText("jLabel1");
        jPanel1.add(lblerror2);
        lblerror2.setBounds(190, 240, 46, 18);

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setLayout(null);

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setPreferredSize(new java.awt.Dimension(426, 115));
        jPanel3.setLayout(null);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 941, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(562, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DiceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DiceMouseClicked
        // TODO add your handling code here:
        // trun이 영이 됬을 경우 처리 X

        Dice dice = new Dice();
        diceResult = dice.rollDice();
        
        diceNum.setText(String.valueOf(diceResult));
        
        if (flag % 2 != 0) {
            int judge = player1.movement(diceResult, locationList);
            getPlayerlblList()[0].setLocation(getPlayerList()[0].getX(), getPlayerList()[0].getY());
            // 메서드/class 위치
            location1.setText(Integer.toString(judge)); // 에러 검출코드

            // 구매 여부
            if (judge == 0) {
                buyFrame.setLocation(350, 350); // Model 대화상자 위치 지정
                buyFrame.setSize(430, 300); // Model 대화상자 크기 지정
                buyFrame.show();

                // 구매가
                JLabel[][] buylblList = {
                    {buyGroundVal, buyVilaVal, buyBuildingVal, buyHotelVal},
                    {sellGroundVal, sellVilaVal, sellBuildingVal, sellHotelVal},
                    {costGroundVal, costVilaVal, costBuildingVal, costHotelVal}
                };
                int[] valueList = {locationList[player1.getReferLoc() - 1].getLoc_ground(),
                    locationList[player1.getReferLoc() - 1].getLoc_vila(),
                    locationList[player1.getReferLoc() - 1].getLoc_building(),
                    locationList[player1.getReferLoc() - 1].getLoc_hotel()
                };
                for (int i = 0; i < 3; i++) {
                    for (int z = 0; z < 4; z++) {
                        if (i == 0) {
                            buylblList[i][z].setText(Integer.toString(valueList[z]));
                        } else if (i == 1) {
                            buylblList[i][z].setText(Integer.toString(valueList[z] / 2));
                        } else {
                            buylblList[i][z].setText(Integer.toString(valueList[z] * 2));
                        }
                    }
                }
                
            } else if (judge == player1.getPlayerNum()) {
                // 재구매 여부

            } else {
                // 통행료 지불

            }
            flag++;
            turn--;
            if (turn == 0) {
                System.exit(-1);
            }
        } else {
            int judge = player2.movement(diceResult, locationList);
            getPlayerlblList()[1].setLocation(getPlayerList()[1].getX(), getPlayerList()[1].getY());
            if (judge == 0) {
                // 구매 여부
            } else if (judge == player2.getPlayerNum()) {

                // 재구매 여부
            } else {
                // 통행료 지불
            }
            flag++;
            turn--;
            if (turn == 0) {
                System.exit(-1);
            }
        }
    }//GEN-LAST:event_DiceMouseClicked

    private void form2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_form2ActionPerformed
        int iWidth = 430;
        int iHeight = 300;
        
        buyFrame.setLocation(350, 350); // Model 대화상자 위치 지정
        buyFrame.setSize(iWidth, iHeight); // Model 대화상자 크기 지정
        buyFrame.show();
        
    }//GEN-LAST:event_form2ActionPerformed

    private void btnBuyConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuyConfirmActionPerformed
        int[] array = {0, 0, 0, 0};
        int totalPrice = 0;
        if (chkLoc_ground.isSelected()) {
            array[0] = 1;
            totalPrice += locationList[playerList[flag % 2 + 1].getReferLoc()].getLoc_ground();
            chkLoc_ground.setSelected(false);
        }
        if (chkLoc_vila.isSelected()) {
            array[1] = 1;
            totalPrice += locationList[playerList[flag % 2 + 1].getReferLoc()].getLoc_ground();
            chkLoc_vila.setSelected(false);
        }
        if (chkLoc_building.isSelected()) {
            array[2] = 1;
            totalPrice += locationList[playerList[flag % 2 + 1].getReferLoc()].getLoc_ground();
            chkLoc_building.setSelected(false);
        }
        if (chkLoc_hotel.isSelected()) {
            array[3] = 1;
            totalPrice += locationList[playerList[flag % 2 + 1].getReferLoc()].getLoc_ground();
            chkLoc_hotel.setSelected(false);
        }
        if (playerList[flag % 2 + 1].getMoney() < totalPrice) {
            // 팝업 처리
        } else {
            int beforeMoney = playerList[flag % 2 + 1].getMoney();
            playerList[flag % 2 + 1].setMoney(beforeMoney -= totalPrice);
        }
        locationList[playerList[flag % 2 + 1].getReferLoc()].setValue(array);
        lblBuyTitle.setText(null);
        buyFrame.hide();
    }//GEN-LAST:event_btnBuyConfirmActionPerformed

    private void buyFrameComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_buyFrameComponentShown
        lblBuyTitle.setText(locationList[player1.getReferLoc() - 1].getLoc_name());
        int[] loc_array = new int[4];
        JCheckBox[] check = {chkLoc_ground, chkLoc_vila, chkLoc_building, chkLoc_hotel};
        
        for (int i = 0; i < loc_array.length; i++) {
            loc_array[i] = locationList[playerList[flag % 2 + 1].getReferLoc()].getValue()[i];
        }
        for (int i = 0; i < loc_array.length; i++) {
            if (loc_array[i] == 1) {
                check[i].setSelected(true);
            }
        }

    }//GEN-LAST:event_buyFrameComponentShown

    private void btnBuyCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuyCancelActionPerformed
        JCheckBox[] checkdelList = {chkLoc_ground, chkLoc_vila, chkLoc_building, chkLoc_hotel};
        for (int i = 0; i < checkdelList.length; i++) {
            checkdelList[i].setSelected(false);
        }
        
        buyFrame.hide();
    }//GEN-LAST:event_btnBuyCancelActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                    
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
            
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
            
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
            
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Dice;
    private javax.swing.JButton btnBuyCancel;
    private javax.swing.JButton btnBuyConfirm;
    private javax.swing.JLabel buyBuildingVal;
    private javax.swing.JFrame buyFrame;
    private javax.swing.JLabel buyGroundVal;
    private javax.swing.JLabel buyHotelVal;
    private javax.swing.JLabel buyVilaVal;
    private javax.swing.JCheckBox chkLoc_building;
    private javax.swing.JCheckBox chkLoc_ground;
    private javax.swing.JCheckBox chkLoc_hotel;
    private javax.swing.JCheckBox chkLoc_vila;
    private javax.swing.JLabel costBuildingVal;
    private javax.swing.JLabel costGroundVal;
    private javax.swing.JLabel costHotelVal;
    private javax.swing.JLabel costVilaVal;
    private javax.swing.JLabel diceNum;
    private javax.swing.JButton form2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblBuy;
    private javax.swing.JLabel lblBuyTitle;
    private javax.swing.JLabel lblCost;
    private javax.swing.JLabel lblPlayer1;
    private javax.swing.JLabel lblPlayer2;
    private javax.swing.JLabel lblSell;
    private javax.swing.JLabel lblerror;
    private javax.swing.JLabel lblerror2;
    private javax.swing.JLabel location1;
    private javax.swing.JLabel location10;
    private javax.swing.JLabel location11;
    private javax.swing.JLabel location12;
    private javax.swing.JLabel location13;
    private javax.swing.JLabel location14;
    private javax.swing.JLabel location15;
    private javax.swing.JLabel location16;
    private javax.swing.JLabel location17;
    private javax.swing.JLabel location18;
    private javax.swing.JLabel location19;
    private javax.swing.JLabel location2;
    private javax.swing.JLabel location20;
    private javax.swing.JLabel location21;
    private javax.swing.JLabel location22;
    private javax.swing.JLabel location23;
    private javax.swing.JLabel location24;
    private javax.swing.JLabel location3;
    private javax.swing.JLabel location4;
    private javax.swing.JLabel location5;
    private javax.swing.JLabel location6;
    private javax.swing.JLabel location7;
    private javax.swing.JLabel location8;
    private javax.swing.JLabel location9;
    private javax.swing.JLabel sellBuildingVal;
    private javax.swing.JLabel sellGroundVal;
    private javax.swing.JLabel sellHotelVal;
    private javax.swing.JLabel sellVilaVal;
    // End of variables declaration//GEN-END:variables
}
