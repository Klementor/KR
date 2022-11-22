import java.awt.*;
import java.awt.event.*;

public class KR extends Panel {
    Panel row1, row1_1, row1_2, row2, p1, p2, p3, row3_1, row3_2, p4, p5;
    Canvas c1, c2;

    Choice choice;
    List list, list2;

    Label l1, l1_2, l2, l3, l3_2;

    Scrollbar scrollbar1, scrollbar2, scrollbar3, scrollbar4;

    String[] colors;
    int quartetsCount = 6;

    Button animationBtn;
    boolean isAnimationEnabled = false;

    boolean isYNext = false;
    int xGold = 0;
    int yGold = 0;
    boolean isYPositive = true;
    boolean isXPositive = true;
    int quartet = -1;
    int animCounter = 1;
    boolean firstAnimPassed = false;
    boolean secondAnimPassed = false;
    boolean thirdAnimPassed = false;


    int k1 = 0, k2 = -100;
    double previousSide = 0;
    double currentRadius = 0;
    Thread thread_1 = null, thread_2 = null, thread_3 = null;

    CheckboxGroup checkboxGroup;
    Checkbox checkbox1, checkbox2;

    TextField textField;
    String text;
    int runStringCounter = 1;

    public KR() {
        colors = new String[9];
        colors[0] = "Белый";
        colors[1] = "Чёрный";
        colors[2] = "Красный";
        colors[3] = "Синий";
        colors[4] = "Зелёный";
        colors[5] = "Жёлтый";
        colors[6] = "Голубой";
        colors[7] = "Серый";
        colors[8] = "Розовый";
        setLayout(new GridLayout(2, 1));

        row1 = new Panel();
        row2 = new Panel();
        row1.setLayout(new GridLayout(1, 3));
        row2.setLayout(new GridLayout(1, 2));
        p1 = new Panel();
        p1.setLayout(new GridLayout(2, 1));
        p3 = new Panel();
        p3.setLayout(new GridLayout(2, 1));
        p2 = new Panel();
        p4 = new Panel();
        p5 = new Panel();
        row1_1 = new Panel();
        row1_2 = new Panel();
        row3_1 = new Panel();
        row3_2 = new Panel();

        add(row1);
        add(row2);
        row1.add(p1);
        row1.add(p2);
        row1.add(p3);
        row2.add(p4);
        row2.add(p5);
        p1.add(row1_1);
        p1.add(row1_2);
        p3.add(row3_1);
        p3.add(row3_2);

        p1.setBackground(new Color(101, 101, 101));
        p2.setBackground(new Color(159, 159, 159));
        p3.setBackground(new Color(101, 101, 101));
        p4.setBackground(new Color(183, 171, 108));
        p5.setBackground(new Color(183, 171, 108));

        //блок 1
        row1_1.setLayout(new BorderLayout());
        l1 = new Label("Цвет квадрата:", Label.CENTER);
        row1_1.add(l1, BorderLayout.NORTH);

        choice = new Choice();
        for (int i = 0; i < 9; ++i) {
            choice.addItem(colors[i]);
        }
        choice.select("Чёрный");
        choice.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                c1.repaint();
            }
        });

        Panel temp_panel = new Panel();
        row1_1.add(temp_panel, BorderLayout.CENTER);
        temp_panel.add(choice);

        l1_2 = new Label("Цвет круга:", Label.CENTER);
        row1_1.add(l1_2, BorderLayout.SOUTH);

        list = new List();
        for (int i = 0; i < 9; ++i) {
            list.add(colors[i]);
        }
        list.select(1);
        list.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                c1.repaint();
            }
        });
        row1_2.add(list);

        //блок 2
        p2.setLayout(new GridLayout(2, 1));
        Panel inner2 = new Panel();
        inner2.setLayout(new GridLayout(3, 1));
        p2.add(inner2);

        l2 = new Label("Блокировка панелей:", Label.CENTER);
        inner2.add(l2);

        checkboxGroup = new CheckboxGroup();
        checkbox1 = new Checkbox("Запретить работу");
        checkbox1.setCheckboxGroup(checkboxGroup);
        checkbox2 = new Checkbox("Разрешить работу");
        checkbox2.setCheckboxGroup(checkboxGroup);
        checkboxGroup.setSelectedCheckbox(checkbox2);

        checkbox1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                choice.setEnabled(false);
                list.setEnabled(false);
                list2.setEnabled(false);
                textField.setEnabled(false);
                scrollbar1.setEnabled(false);
                scrollbar2.setEnabled(false);
                scrollbar3.setEnabled(false);
                scrollbar4.setEnabled(false);
            }
        });

        checkbox2.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                choice.setEnabled(true);
                list.setEnabled(true);
                list2.setEnabled(true);
                textField.setEnabled(true);
                scrollbar1.setEnabled(true);
                scrollbar2.setEnabled(true);
                scrollbar3.setEnabled(true);
                scrollbar4.setEnabled(true);
            }
        });

        inner2.add(checkbox1);
        inner2.add(checkbox2);

        animationBtn = new Button("Переключить анимацию");
        Panel inner2_3 = new Panel();
        inner2_3.setLayout(new BorderLayout());
        p2.add(inner2_3);
        animationBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isAnimationEnabled) {
                    isAnimationEnabled = false;
                    return;
                } else {
                    isAnimationEnabled = true;
                }

                thread_1 = new FirstThread();
                thread_2 = new SecondThread();
                thread_3 = new ThirdThread();
                thread_1.start();
                thread_3.start();
                thread_2.start();
            }
        });
        inner2_3.add(animationBtn, BorderLayout.SOUTH);

        //блок 3
        row3_1.setLayout(new BorderLayout());
        l3 = new Label("Введите текст:", Label.CENTER);
        row3_1.add(l3, BorderLayout.NORTH);

        textField = new TextField("Text...");
        text = textField.getText();
        Panel inner3 = new Panel();
        textField.addTextListener(new TextListener() {
            public void textValueChanged(TextEvent e) {
                text = textField.getText();
                c2.repaint();
            }
        });
        inner3.add(textField);
        row3_1.add(inner3, BorderLayout.CENTER);
        l3_2 = new Label("Выберите шрифт:", Label.CENTER);
        row3_1.add(l3_2, BorderLayout.SOUTH);

        list2 = new List();
        list2.add("Serif");
        list2.add("Dialog");
        list2.add("SansSerif");
        list2.add("Arial");
        list2.add("Consolas");
        list2.add("Monospaced");
        list2.select(0);
        list2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                c2.repaint();
            }
        });
        row3_2.add(list2);

        //блок 4
        p4.setLayout(new BorderLayout());
        scrollbar1 = new Scrollbar(Scrollbar.VERTICAL);
        scrollbar1.setMinimum(10);
        scrollbar1.setMaximum(100);
        scrollbar1.addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                c1.repaint();
            }
        });
        p4.add(scrollbar1, BorderLayout.EAST);
        scrollbar2 = new Scrollbar(Scrollbar.HORIZONTAL);
        scrollbar2.setMinimum(10);
        scrollbar2.setMaximum(100);
        scrollbar2.addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                c1.repaint();
            }
        });
        p4.add(scrollbar2, BorderLayout.SOUTH);

        //блок 5
        p5.setLayout(new BorderLayout());
        scrollbar3 = new Scrollbar(Scrollbar.VERTICAL);
        scrollbar3.setMinimum(30);
        scrollbar3.setMaximum(100);
        scrollbar3.addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                c2.repaint();
            }
        });
        p5.add(scrollbar3, BorderLayout.WEST);
        scrollbar4 = new Scrollbar(Scrollbar.HORIZONTAL);
        scrollbar4.setMinimum(20);
        scrollbar4.setMaximum(90);
        scrollbar4.addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                c2.repaint();
            }
        });
        p5.add(scrollbar4, BorderLayout.SOUTH);

        //работа с холстами
        c1 = new Canvas() {
            Image buffer;
            Graphics gc;

            public void update(Graphics g) {
                paint(g);
            }

            public void paint(Graphics g) {
                buffer = createImage(getSize().width, getSize().height);
                gc = buffer.getGraphics();
                draw();
                g.drawImage(buffer, 0, 0, this);
            }

            public void draw() {
                //Отрисовка круга и прямоугольника

                int canvas_h = c1.getHeight();
                int canvas_w = c1.getWidth();
                int minSide = Math.min(canvas_h, canvas_w);

                if (isAnimationEnabled) {
                    //Отрисовка золотого сечения
                    gc.setColor(getColorByString(choice.getSelectedItem()));
                    int side = minSide / 10;
                    double r = currentRadius;

                    double xFirst = xGold + r *
                            Math.cos(Math.toRadians(
                                    (90 * quartetsCount * (double) Math.abs(k1) / 100) + 270)
                            );
                    double yFirst = yGold + r *
                            Math.sin(Math.toRadians(
                                    (90 * quartetsCount * (double) Math.abs(k1) / 100) + 270)
                            );
                    gc.fillRect((int) xFirst, (int) yFirst, side, side);

                    //Отрисовка круга
                    gc.setColor(getColorByString(list.getSelectedItem()));
                    int w = (int) ((minSide - (2 * side + minSide / 20)) * 0.01 * Math.abs(Math.abs(k2) - 100) + side);
                    int x2 = (canvas_w / 20) * 19 - w;
                    int y2 = canvas_h / 20;
                    gc.fillOval(x2, y2, w, w);

                    return;
                }

                int w = (int) ((minSide) * 0.01 * scrollbar1.getValue());
                int w2 = (int) ((minSide) * 0.01 * scrollbar2.getValue());

                int x1 = (canvas_w / 20);
                int x2 = (canvas_w / 20) * 19 - w2;
                int y1 = canvas_h / 20;
                int y2 = canvas_h / 20;

                gc.setColor(getColorByString(choice.getSelectedItem()));
                gc.fillRect(x1, y1, w, w);
                gc.setColor(getColorByString(list.getSelectedItem()));
                gc.fillOval(x2, y2, w2, w2);
            }
        };
        p4.add(c1, BorderLayout.CENTER);

        c2 = new Canvas() {
            Image buffer;
            Graphics gc;

            public void update(Graphics g) {
                paint(g);
            }

            public void paint(Graphics g) {
                buffer = createImage(getSize().width, getSize().height);
                gc = buffer.getGraphics();
                draw();
                g.drawImage(buffer, 0, 0, this);
            }

            public void draw() {
                //Отрисовка текста
                String text = textField.getText();
                gc.setFont(new Font(list2.getSelectedItem(), Font.PLAIN, scrollbar4.getValue()));
                FontMetrics fm = getFontMetrics(gc.getFont());
                int canvas_h = c2.getHeight();
                int canvas_w = c2.getWidth();
                double h = scrollbar3.getValue() * 0.01 * canvas_h;
                if (isAnimationEnabled) {
                    text = KR.this.text;
                    gc.drawString(text, canvas_w / 2 - fm.stringWidth(text) / 2, (int) h);
                }
                gc.drawString(text, canvas_w / 2 - fm.stringWidth(text) / 2, (int) h);
            }
        };
        p5.add(c2, BorderLayout.CENTER);
    }

    synchronized public void moveObj1() throws InterruptedException {
        if (animCounter > 1) {
            wait();
        } else {
            c1.repaint();
            int currentQuartet = k1 / (100 / quartetsCount);
            if (currentQuartet == quartetsCount) {
                quartet = currentQuartet;
                clear();
                animCounter++;
                wait();
            } else {
                if (currentQuartet != quartet) {
                    quartet = currentQuartet;
                    if (xGold == 0 && yGold == 0) {
                        currentRadius = (c1.getHeight() - (c1.getHeight() / 10 + c1.getHeight() / 20)) / 1.61803398;
                        yGold = (int) (currentRadius);
                    } else {
                        currentRadius = previousSide / 1.61803398;
                        double totalValue = previousSide - currentRadius;
                        if (isYNext) {
                            if (isYPositive) {
                                yGold += totalValue;
                            } else {
                                yGold -= totalValue;
                            }
                            isYPositive = !isYPositive;
                        } else {
                            if (isXPositive) {
                                xGold += totalValue;
                            } else {
                                xGold -= totalValue;
                            }
                            isXPositive = !isXPositive;
                        }
                        isYNext = !isYNext;
                    }
                    previousSide = currentRadius;
                }
                k1++;
            }
        }
    }

    synchronized public void moveObj2() throws InterruptedException {
        if (animCounter != 2) {
            wait();
        } else {
            c1.repaint();
            if (k2 > 99) {
                clear();
                animCounter++;
                wait();
            } else {
                k2++;
            }
        }
    }

    synchronized public void printText() throws InterruptedException {
        if (animCounter != 3) {
            wait();
        } else {
            if (runStringCounter == text.length()) {
                clear();
                animCounter = 1;
                wait();
            } else {
                c2.repaint();
                char ch = text.charAt(0);
                text = text.substring(1);
                text += ch;
                runStringCounter++;
            }
        }
    }

    synchronized public void clear() {
        quartet = -1;
        xGold = 0;
        yGold = 0;
        isYNext = false;
        isXPositive = true;
        isYPositive = true;
        currentRadius = 0;
        previousSide = 0;
        k1 = 0;
        k2 = -100;
        text = textField.getText();
        runStringCounter = 0;
        notifyAll();
    }

    // Внутренний класс, производный от класса Thread
    class FirstThread extends Thread {
        // Переопределение метода run, выполняемого как поток команд
        // объекта типа FirstThread
        public void run() {
            while (isAnimationEnabled) {
                try {
                    // Вызов метода для приостановки потока на 50 мс
                    Thread.sleep(30);

                    moveObj1();
                } catch (InterruptedException e) {
                    // здесь можно вернуть управление из метода run,хотя это необязательно,
                    // т.к. ни один другой поток не прерывает работу данного и InterruptedException
                    // никогда не будет сгенерировано (но его обработки требует компилятор)
                    return;
                }
            }
            clear();
        }
    }

    // Внутренний класс, производный от класса Thread
    class SecondThread extends Thread {
        // Переопределение метода run, выполняемого как поток команд
        public void run() {
            while (isAnimationEnabled) {
                try {
                    // Вызов метода для приостановки потока на 20 мс
                    Thread.sleep(30);

                    moveObj2();
                } catch (InterruptedException e) {
                    // здесь можно вернуть управление из метода run,хотя это необязательно,
                    // т.к. ни один другой поток не прерывает работу данного и InterruptedException
                    // никогда не будет сгенерировано (но его обработки требует компилятор)
                    return;
                }
            }
            clear();
        }
    }

    // Внутренний класс, производный от класса Thread
    class ThirdThread extends Thread {
        // Переопределение метода run, выполняемого как поток команд
        public void run() {
            while (isAnimationEnabled) {
                try {
                    // Вызов метода для приостановки потока на 20 мс
                    Thread.sleep(200);

                    printText();
                } catch (InterruptedException e) {
                    // здесь можно вернуть управление из метода run,хотя это необязательно,
                    // т.к. ни один другой поток не прерывает работу данного и InterruptedException
                    // никогда не будет сгенерировано (но его обработки требует компилятор)
                    return;
                }
            }
            clear();
        }
    }

    Color getColorByString(String str) {
        switch (str) {
            case "Белый":
                return new Color(255, 255, 255);
            case "Красный":
                return new Color(255, 0, 0);
            case "Зелёный":
                return new Color(0, 255, 0);
            case "Синий":
                return new Color(0, 0, 255);
            case "Жёлтый":
                return new Color(255, 255, 0);
            case "Чёрный":
                return new Color(0, 0, 0);
            case "Голубой":
                return new Color(0, 139, 164, 255);
            case "Серый":
                return new Color(175, 175, 175, 255);
            case "Розовый":
                return new Color(255, 0, 136, 255);
        }
        return new Color(0, 0, 0);
    }

    // Точка хода в программу
    public static void main(String args[]) {
        // Объявления и инициализация переменной типа GreetApplication
        KR panel = new KR();

  /* Шаблон для размещения компонента-"холста" в контейнере-окне
     -----------------------------------------------------------
     Объявления и инициализация переменной типа java.awt.Frame,
     размещение компонента в окне-фрейме,
     установка размеров окна,
     установка местоположения окна на экране,
     установка видимости окна на экране,
     регистрация приемника события закрытия окна.
  */
        Frame frame = new Frame("An AWT-Based Application");
        frame.add(panel);
        frame.setSize(500, 500);
        frame.setLocation(100, 100);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}

/* Синтаксис new Canvas(){} указывает компилятору, что код в блоке {} определяет
   анонимный внутренний класс. В результате компиляции будет получен файл класса
   с именем имя_ внешнего_класса$№.class.
   Внутренний класс имеет доступ ко всем полям и методам своего внешнего класса
   (в том числе и private) как к собственным. Элементы внутреннего класса,
   напротив, известны только в пределах этого класса и не могут использоваться
   внешним классом непосредственно.
*/


