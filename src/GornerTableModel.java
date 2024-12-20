package lab3v2;

import javax.swing.table.AbstractTableModel;
@SuppressWarnings("serial")
public class GornerTableModel extends AbstractTableModel {
    private Double[] coefficients;
    private Double from;
    private Double to;
    private Double step;

    public GornerTableModel(Double from, Double to, Double step, Double[] coefficients) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.coefficients = coefficients;
    }

    public Double getFrom() {
        return from;
    }

    public Double getTo() {
        return to;
    }

    public Double getStep() {
        return step;
    }

    public int getColumnCount() {
        // В данной модели три столбца
        return 3;  // изменили на 3
    }

    public int getRowCount() {
        // Вычислить количество точек между началом и концом отрезка
        // исходя из шага табулирования
        return new Double(Math.ceil((to - from) / step)).intValue() + 1;
    }

    public Object getValueAt(int row, int col) {
        // Вычислить значение X как НАЧАЛО_ОТРЕЗКА + ШАГ * НОМЕР_СТРОКИ
        double x = from + step * row;

        if (col == 0) {
            // Если запрашивается значение 1-го столбца, то это X
            return x;
        } else if (col == 1) {
            // Если запрашивается значение 2-го столбца, то это значение
            // многочлена
            Double result = 0.0;
            // Вычисление значения в точке по схеме Горнера.
            result = coefficients[0]; // Начинаем с коэффициента при старшей степени
            for (int i = 1; i < coefficients.length; i++) {
                result = result * x + coefficients[i];
            }
            return result;
        } else {
            // Если запрашивается значение 3-го столбца, то проверяем, является ли дробная часть 0
            Double result = 0.0;
            result = coefficients[0];
            for (int i = 1; i < coefficients.length; i++) {
                result = result * x + coefficients[i];
            }

            // Проверка, является ли дробная часть числа равной нулю
            return result % 1 == 0; // Возвращаем true, если дробная часть 0, иначе false
        }
    }

    public String getColumnName(int col) {
        switch (col) {
            case 0:
                // Название 1-го столбца
                return "Значение X";
            case 1:
                // Название 2-го столбца
                return "Значение многочлена";
            case 2:
                // Название 3-го столбца
                return "Точное значение?";  // новое имя столбца
            default:
                return "";
        }
    }

    public Class<?> getColumnClass(int col) {
        // В 1-ом и 2-ом столбцах значения типа Double, в 3-м столбце - Boolean
        if (col == 2) {
            return Boolean.class; // 3-й столбец будет содержать булевы значения
        }
        return Double.class;
    }
}