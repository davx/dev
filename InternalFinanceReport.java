 private void setupQuery() {
        sqlQuery = null;
        if (filterPerExpenseCategoryButton.isSelected()) {
            if (dollarRadio.isSelected()) {

                sqlQuery = new StringBuilder("SELECT \n"
                        + "                     c.name AS name, \n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 1, dpd.amount, 0)) AS JAN, \n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 2, dpd.amount, 0)) AS FEB, \n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 3, dpd.amount, 0)) AS MAR, \n"
                        + "                     SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3), dpd.amount, 0)) AS '1/ 4분기 / SUBDIVISION',\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 4, dpd.amount, 0)) AS APR,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 5, dpd.amount, 0)) AS MAY,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 6, dpd.amount, 0)) AS JUN,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) IN (4, 5, 6), dpd.amount, 0)) AS '2/ 4분기 / SUBDIVISION',\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 7, dpd.amount, 0)) AS JUL,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 8, dpd.amount, 0)) AS AUGUST,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 9, dpd.amount, 0)) AS SEPT,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) IN (7, 8, 9), dpd.amount, 0)) AS '3/ 4분기 / SUBDIVISION',\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 10, dpd.amount, 0)) AS OCTUBER,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 11, dpd.amount, 0)) AS NOV,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 12, dpd.amount, 0)) AS DIC,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) IN (10, 11, 12), dpd.amount, 0)) AS '4/ 4분기 / SUBDIVISION', \n"
                        + "                     SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), dpd.amount, 0)) AS 'G. TOTAL', \n"
                        + "                     'FIXED' AS expense_group \n"
                        + "                     FROM voucher_dollars AS vd \n"
                        + "                     JOIN purchases_vouchers_dollars_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                        + "                     JOIN purchases_dollars pd ON pd.id = pvdv.id_purchase \n"
                        + "                     JOIN providers pr ON pd.provider = pr.id \n"
                        + "                     JOIN expense_category c ON pr.category = c.id \n"
                        + "                     JOIN description_purchases_dollars dpd ON dpd.purchase = pd.id WHERE \n");

                if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'KCI' GROUP BY c.name ");
                }

                if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'SQA' GROUP BY c.name ");
                }

                if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' GROUP BY c.name ");
                }

                if (!conditions.isEmpty()) {
                    sqlQuery.append(String.join(" AND ", conditions));
                    conditions.clear();
                }

                sqlQuery.append(" UNION ALL ");

                sqlQuery.append("SELECT 'TOTAL FIJOS' AS name, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 1, dpd.amount, 0)) AS JAN, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 2, dpd.amount, 0)) AS FEB, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 3, dpd.amount, 0)) AS MAR, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3), dpd.amount, 0)) AS '1/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 4, dpd.amount, 0)) AS APR, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 5, dpd.amount, 0)) AS MAY, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 6, dpd.amount, 0)) AS JUN, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (4, 5, 6), dpd.amount, 0)) AS '2/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 7, dpd.amount, 0)) AS JUL, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 8, dpd.amount, 0)) AS AUGUST, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 9, dpd.amount, 0)) AS SEPT, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (7, 8, 9), dpd.amount, 0)) AS '3/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 10, dpd.amount, 0)) AS OCTUBER, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 11, dpd.amount, 0)) AS NOV, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 12, dpd.amount, 0)) AS DIC, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (10, 11, 12), dpd.amount, 0)) AS '4/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), dpd.amount, 0)) AS 'G. TOTAL', \n"
                        + "    'FIXED' AS expense_group \n"
                        + "FROM voucher_dollars AS vd \n"
                        + "JOIN purchases_vouchers_dollars_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                        + "JOIN purchases_dollars pd ON pd.id = pvdv.id_purchase \n"
                        + "JOIN providers pr ON pd.provider = pr.id \n"
                        + "JOIN expense_category c ON pr.category = c.id \n"
                        + "JOIN description_purchases_dollars dpd ON dpd.purchase = pd.id \n"
                        + "WHERE");

                if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'KCI' GROUP BY expense_group ");
                }

                if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'SQA' GROUP BY expense_group ");
                }

                if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' GROUP BY expense_group ");
                }

                if (!conditions.isEmpty()) {
                    sqlQuery.append(String.join(" AND ", conditions));
                    conditions.clear();
                }

                sqlQuery.append(" UNION ALL ");

                sqlQuery.append("SELECT c.name AS name, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 1, dpd.amount, 0)) AS JAN, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 2, dpd.amount, 0)) AS FEB, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 3, dpd.amount, 0)) AS MAR, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3), dpd.amount, 0)) AS '1/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 4, dpd.amount, 0)) AS APR, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 5, dpd.amount, 0)) AS MAY, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 6, dpd.amount, 0)) AS JUN, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (4, 5, 6), dpd.amount, 0)) AS '2/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 7, dpd.amount, 0)) AS JUL, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 8, dpd.amount, 0)) AS AUGUST, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 9, dpd.amount, 0)) AS SEPT, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (7, 8, 9), dpd.amount, 0)) AS '3/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 10, dpd.amount, 0)) AS OCTUBER, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 11, dpd.amount, 0)) AS NOV, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 12, dpd.amount, 0)) AS DIC, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (10, 11, 12), dpd.amount, 0)) AS '4/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), dpd.amount, 0)) AS 'G. TOTAL', \n"
                        + "    'MATERIAL' AS expense_group \n"
                        + "FROM voucher_dollars AS vd \n"
                        + "JOIN purchases_vouchers_dollars_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                        + "JOIN purchases_dollars pd ON pd.id = pvdv.id_purchase \n"
                        + "JOIN providers pr ON pd.provider = pr.id \n"
                        + "JOIN expense_category c ON pr.category = c.id \n"
                        + "JOIN description_purchases_dollars dpd ON dpd.purchase = pd.id \n"
                        + "WHERE");

                if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'KCI' GROUP BY c.name ");
                }

                if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'SQA' GROUP BY c.name ");
                }

                if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' GROUP BY c.name ");
                }

                if (!conditions.isEmpty()) {
                    sqlQuery.append(String.join(" AND ", conditions));
                    conditions.clear();
                }

                sqlQuery.append(" UNION ALL ");

                sqlQuery.append("SELECT 'TOTAL MATERIALES' AS name, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 1, dpd.amount, 0)) AS JAN, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 2, dpd.amount, 0)) AS FEB, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 3, dpd.amount, 0)) AS MAR, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3), dpd.amount, 0)) AS '1/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 4, dpd.amount, 0)) AS APR, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 5, dpd.amount, 0)) AS MAY, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 6, dpd.amount, 0)) AS JUN, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (4, 5, 6), dpd.amount, 0)) AS '2/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 7, dpd.amount, 0)) AS JUL, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 8, dpd.amount, 0)) AS AUGUST, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 9, dpd.amount, 0)) AS SEPT, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (7, 8, 9), dpd.amount, 0)) AS '3/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 10, dpd.amount, 0)) AS OCTUBER, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 11, dpd.amount, 0)) AS NOV, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 12, dpd.amount, 0)) AS DIC, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (10, 11, 12), dpd.amount, 0)) AS '4/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), dpd.amount, 0)) AS 'G. TOTAL', \n"
                        + "    'MATERIAL' AS expense_group \n"
                        + "FROM voucher_dollars AS vd \n"
                        + "JOIN purchases_vouchers_dollars_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                        + "JOIN purchases_dollars pd ON pd.id = pvdv.id_purchase \n"
                        + "JOIN providers pr ON pd.provider = pr.id \n"
                        + "JOIN expense_category c ON pr.category = c.id \n"
                        + "JOIN description_purchases_dollars dpd ON dpd.purchase = pd.id \n"
                        + "WHERE");

                if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'KCI' GROUP BY expense_group ");
                }

                if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'SQA' GROUP BY expense_group ");
                }

                if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' GROUP BY expense_group ");
                }

                if (!conditions.isEmpty()) {
                    sqlQuery.append(String.join(" AND ", conditions));
                    conditions.clear();
                }

                if (apparelKCICheckBox.isSelected()) {
                    sqlQuery.append(" UNION ALL ");

                    sqlQuery.append("SELECT 'BUYERS BILLING' as name,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) = 1, bb.amount, 0)) AS JAN,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) = 2, bb.amount, 0)) AS FEB,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) = 3, bb.amount, 0)) AS MAR,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) IN (1, 2, 3), bb.amount, 0)) AS '1/ 4분기 / SUBDIVISION',\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) = 4, bb.amount, 0)) AS APR,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) = 5, bb.amount, 0)) AS MAY,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) = 6, bb.amount, 0)) AS JUN,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) IN (4, 5, 6), bb.amount, 0)) AS '2/ 4분기 / SUBDIVISION',\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) = 7, bb.amount, 0)) AS JUL,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) = 8, bb.amount, 0)) AS AUGUST,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) = 9, bb.amount, 0)) AS SEPT,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) IN (7, 8, 9), bb.amount, 0)) AS '3/ 4분기 / SUBDIVISION',\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) = 10, bb.amount, 0)) AS OCTUBER,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) = 11, bb.amount, 0)) AS NOV,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) = 12, bb.amount, 0)) AS DIC,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) IN (10, 11, 12), bb.amount, 0)) AS '4/ 4분기 / SUBDIVISION',\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), bb.amount, 0)) AS 'G. TOTAL',\n"
                            + "    'SALES' AS expense_group\n"
                            + "FROM billing_buyers AS BB\n"
                            + "WHERE YEAR(bb.emissionDate) = ? \n"
                            + "GROUP BY expense_group");

                }
            }

            if (quetzalesRadio.isSelected()) {

                sqlQuery = new StringBuilder("SELECT \n"
                        + "                     c.name AS name, \n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 1, dpd.amount, 0)) AS JAN, \n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 2, dpd.amount, 0)) AS FEB, \n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 3, dpd.amount, 0)) AS MAR, \n"
                        + "                     SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3), dpd.amount, 0)) AS '1/ 4분기 / SUBDIVISION',\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 4, dpd.amount, 0)) AS APR,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 5, dpd.amount, 0)) AS MAY,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 6, dpd.amount, 0)) AS JUN,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) IN (4, 5, 6), dpd.amount, 0)) AS '2/ 4분기 / SUBDIVISION',\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 7, dpd.amount, 0)) AS JUL,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 8, dpd.amount, 0)) AS AUGUST,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 9, dpd.amount, 0)) AS SEPT,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) IN (7, 8, 9), dpd.amount, 0)) AS '3/ 4분기 / SUBDIVISION',\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 10, dpd.amount, 0)) AS OCTUBER,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 11, dpd.amount, 0)) AS NOV,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 12, dpd.amount, 0)) AS DIC,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) IN (10, 11, 12), dpd.amount, 0)) AS '4/ 4분기 / SUBDIVISION', \n"
                        + "                     SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), dpd.amount, 0)) AS 'G. TOTAL', \n"
                        + "                     'FIXED' AS expense_group \n"
                        + "                     FROM voucher_quetzales AS vd \n"
                        + "                     JOIN purchases_vouchers_quetzales_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                        + "                     JOIN purchases_quetzales pd ON pd.id = pvdv.id_purchase \n"
                        + "                     JOIN providers pr ON pd.provider = pr.id \n"
                        + "                     JOIN expense_category c ON pr.category = c.id \n"
                        + "                     JOIN description_purchases_quetzales dpd ON dpd.purchase = pd.id WHERE \n");

                if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'KCI' GROUP BY c.name ");
                }

                if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'SQA' GROUP BY c.name ");
                }

                if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' GROUP BY c.name ");
                }

                if (!conditions.isEmpty()) {
                    sqlQuery.append(String.join(" AND ", conditions));
                    conditions.clear();
                }

                sqlQuery.append(" UNION ALL ");

                sqlQuery.append("SELECT 'TOTAL FIJOS' AS name, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 1, dpd.amount, 0)) AS JAN, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 2, dpd.amount, 0)) AS FEB, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 3, dpd.amount, 0)) AS MAR, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3), dpd.amount, 0)) AS '1/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 4, dpd.amount, 0)) AS APR, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 5, dpd.amount, 0)) AS MAY, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 6, dpd.amount, 0)) AS JUN, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (4, 5, 6), dpd.amount, 0)) AS '2/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 7, dpd.amount, 0)) AS JUL, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 8, dpd.amount, 0)) AS AUGUST, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 9, dpd.amount, 0)) AS SEPT, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (7, 8, 9), dpd.amount, 0)) AS '3/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 10, dpd.amount, 0)) AS OCTUBER, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 11, dpd.amount, 0)) AS NOV, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 12, dpd.amount, 0)) AS DIC, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (10, 11, 12), dpd.amount, 0)) AS '4/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), dpd.amount, 0)) AS 'G. TOTAL', \n"
                        + "    'FIXED' AS expense_group \n"
                        + "FROM voucher_quetzales AS vd \n"
                        + "JOIN purchases_vouchers_quetzales_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                        + "JOIN purchases_quetzales pd ON pd.id = pvdv.id_purchase \n"
                        + "JOIN providers pr ON pd.provider = pr.id \n"
                        + "JOIN expense_category c ON pr.category = c.id \n"
                        + "JOIN description_purchases_quetzales dpd ON dpd.purchase = pd.id \n"
                        + "WHERE");

                if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'KCI' GROUP BY expense_group ");
                }

                if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'SQA' GROUP BY expense_group ");
                }

                if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' GROUP BY expense_group ");
                }

                if (!conditions.isEmpty()) {
                    sqlQuery.append(String.join(" AND ", conditions));
                    conditions.clear();
                }

                sqlQuery.append(" UNION ALL ");

                sqlQuery.append("SELECT c.name AS name, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 1, dpd.amount, 0)) AS JAN, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 2, dpd.amount, 0)) AS FEB, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 3, dpd.amount, 0)) AS MAR, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3), dpd.amount, 0)) AS '1/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 4, dpd.amount, 0)) AS APR, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 5, dpd.amount, 0)) AS MAY, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 6, dpd.amount, 0)) AS JUN, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (4, 5, 6), dpd.amount, 0)) AS '2/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 7, dpd.amount, 0)) AS JUL, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 8, dpd.amount, 0)) AS AUGUST, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 9, dpd.amount, 0)) AS SEPT, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (7, 8, 9), dpd.amount, 0)) AS '3/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 10, dpd.amount, 0)) AS OCTUBER, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 11, dpd.amount, 0)) AS NOV, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 12, dpd.amount, 0)) AS DIC, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (10, 11, 12), dpd.amount, 0)) AS '4/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), dpd.amount, 0)) AS 'G. TOTAL', \n"
                        + "    'MATERIAL' AS expense_group \n"
                        + "FROM voucher_quetzales AS vd \n"
                        + "JOIN purchases_vouchers_quetzales_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                        + "JOIN purchases_quetzales pd ON pd.id = pvdv.id_purchase \n"
                        + "JOIN providers pr ON pd.provider = pr.id \n"
                        + "JOIN expense_category c ON pr.category = c.id \n"
                        + "JOIN description_purchases_quetzales dpd ON dpd.purchase = pd.id \n"
                        + "WHERE");

                if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'KCI' GROUP BY c.name ");
                }

                if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'SQA' GROUP BY c.name ");
                }

                if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' GROUP BY c.name ");
                }

                if (!conditions.isEmpty()) {
                    sqlQuery.append(String.join(" AND ", conditions));
                    conditions.clear();
                }

                sqlQuery.append(" UNION ALL ");

                sqlQuery.append("SELECT 'TOTAL MATERIALES' AS name, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 1, dpd.amount, 0)) AS JAN, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 2, dpd.amount, 0)) AS FEB, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 3, dpd.amount, 0)) AS MAR, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3), dpd.amount, 0)) AS '1/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 4, dpd.amount, 0)) AS APR, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 5, dpd.amount, 0)) AS MAY, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 6, dpd.amount, 0)) AS JUN, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (4, 5, 6), dpd.amount, 0)) AS '2/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 7, dpd.amount, 0)) AS JUL, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 8, dpd.amount, 0)) AS AUGUST, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 9, dpd.amount, 0)) AS SEPT, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (7, 8, 9), dpd.amount, 0)) AS '3/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 10, dpd.amount, 0)) AS OCTUBER, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 11, dpd.amount, 0)) AS NOV, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 12, dpd.amount, 0)) AS DIC, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (10, 11, 12), dpd.amount, 0)) AS '4/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), dpd.amount, 0)) AS 'G. TOTAL', \n"
                        + "    'MATERIAL' AS expense_group \n"
                        + "FROM voucher_quetzales AS vd \n"
                        + "JOIN purchases_vouchers_quetzales_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                        + "JOIN purchases_quetzales pd ON pd.id = pvdv.id_purchase \n"
                        + "JOIN providers pr ON pd.provider = pr.id \n"
                        + "JOIN expense_category c ON pr.category = c.id \n"
                        + "JOIN description_purchases_quetzales dpd ON dpd.purchase = pd.id \n"
                        + "WHERE");

                if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'KCI' GROUP BY expense_group ");
                }

                if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'SQA' GROUP BY expense_group ");
                }

                if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' GROUP BY expense_group ");
                }

                if (!conditions.isEmpty()) {
                    sqlQuery.append(String.join(" AND ", conditions));
                    conditions.clear();
                }

            }

            if (quetzalesRadio.isSelected()) {

                sqlQuery = new StringBuilder("SELECT \n"
                        + "                     c.name AS name, \n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 1, dpd.amount, 0)) AS JAN, \n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 2, dpd.amount, 0)) AS FEB, \n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 3, dpd.amount, 0)) AS MAR, \n"
                        + "                     SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3), dpd.amount, 0)) AS '1/ 4분기 / SUBDIVISION',\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 4, dpd.amount, 0)) AS APR,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 5, dpd.amount, 0)) AS MAY,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 6, dpd.amount, 0)) AS JUN,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) IN (4, 5, 6), dpd.amount, 0)) AS '2/ 4분기 / SUBDIVISION',\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 7, dpd.amount, 0)) AS JUL,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 8, dpd.amount, 0)) AS AUGUST,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 9, dpd.amount, 0)) AS SEPT,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) IN (7, 8, 9), dpd.amount, 0)) AS '3/ 4분기 / SUBDIVISION',\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 10, dpd.amount, 0)) AS OCTUBER,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 11, dpd.amount, 0)) AS NOV,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 12, dpd.amount, 0)) AS DIC,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) IN (10, 11, 12), dpd.amount, 0)) AS '4/ 4분기 / SUBDIVISION', \n"
                        + "                     SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), dpd.amount, 0)) AS 'G. TOTAL', \n"
                        + "                     'FIXED' AS expense_group \n"
                        + "                     FROM voucher_quetzales AS vd \n"
                        + "                     JOIN purchases_vouchers_quetzales_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                        + "                     JOIN purchases_quetzales pd ON pd.id = pvdv.id_purchase \n"
                        + "                     JOIN providers pr ON pd.provider = pr.id \n"
                        + "                     JOIN expense_category c ON pr.category = c.id \n"
                        + "                     JOIN description_purchases_quetzales dpd ON dpd.purchase = pd.id WHERE \n");

                if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'KCI' GROUP BY c.name ");
                }

                if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'SQA' GROUP BY c.name ");
                }

                if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' GROUP BY c.name ");
                }

                if (!conditions.isEmpty()) {
                    sqlQuery.append(String.join(" AND ", conditions));
                    conditions.clear();
                }

                sqlQuery.append(" UNION ALL ");

                sqlQuery.append("SELECT 'TOTAL FIJOS' AS name, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 1, dpd.amount, 0)) AS JAN, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 2, dpd.amount, 0)) AS FEB, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 3, dpd.amount, 0)) AS MAR, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3), dpd.amount, 0)) AS '1/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 4, dpd.amount, 0)) AS APR, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 5, dpd.amount, 0)) AS MAY, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 6, dpd.amount, 0)) AS JUN, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (4, 5, 6), dpd.amount, 0)) AS '2/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 7, dpd.amount, 0)) AS JUL, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 8, dpd.amount, 0)) AS AUGUST, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 9, dpd.amount, 0)) AS SEPT, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (7, 8, 9), dpd.amount, 0)) AS '3/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 10, dpd.amount, 0)) AS OCTUBER, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 11, dpd.amount, 0)) AS NOV, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 12, dpd.amount, 0)) AS DIC, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (10, 11, 12), dpd.amount, 0)) AS '4/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), dpd.amount, 0)) AS 'G. TOTAL', \n"
                        + "    'FIXED' AS expense_group \n"
                        + "FROM voucher_quetzales AS vd \n"
                        + "JOIN purchases_vouchers_quetzales_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                        + "JOIN purchases_quetzales pd ON pd.id = pvdv.id_purchase \n"
                        + "JOIN providers pr ON pd.provider = pr.id \n"
                        + "JOIN expense_category c ON pr.category = c.id \n"
                        + "JOIN description_purchases_quetzales dpd ON dpd.purchase = pd.id \n"
                        + "WHERE");

                if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'KCI' GROUP BY expense_group ");
                }

                if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'SQA' GROUP BY expense_group ");
                }

                if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' GROUP BY expense_group ");
                }

                if (!conditions.isEmpty()) {
                    sqlQuery.append(String.join(" AND ", conditions));
                    conditions.clear();
                }

                sqlQuery.append(" UNION ALL ");

                sqlQuery.append("SELECT c.name AS name, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 1, dpd.amount, 0)) AS JAN, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 2, dpd.amount, 0)) AS FEB, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 3, dpd.amount, 0)) AS MAR, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3), dpd.amount, 0)) AS '1/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 4, dpd.amount, 0)) AS APR, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 5, dpd.amount, 0)) AS MAY, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 6, dpd.amount, 0)) AS JUN, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (4, 5, 6), dpd.amount, 0)) AS '2/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 7, dpd.amount, 0)) AS JUL, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 8, dpd.amount, 0)) AS AUGUST, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 9, dpd.amount, 0)) AS SEPT, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (7, 8, 9), dpd.amount, 0)) AS '3/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 10, dpd.amount, 0)) AS OCTUBER, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 11, dpd.amount, 0)) AS NOV, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 12, dpd.amount, 0)) AS DIC, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (10, 11, 12), dpd.amount, 0)) AS '4/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), dpd.amount, 0)) AS 'G. TOTAL', \n"
                        + "    'MATERIAL' AS expense_group \n"
                        + "FROM voucher_quetzales AS vd \n"
                        + "JOIN purchases_vouchers_quetzales_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                        + "JOIN purchases_quetzales pd ON pd.id = pvdv.id_purchase \n"
                        + "JOIN providers pr ON pd.provider = pr.id \n"
                        + "JOIN expense_category c ON pr.category = c.id \n"
                        + "JOIN description_purchases_quetzales dpd ON dpd.purchase = pd.id \n"
                        + "WHERE");

                if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'KCI' GROUP BY c.name ");
                }

                if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'SQA' GROUP BY c.name ");
                }

                if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' GROUP BY c.name ");
                }

                if (!conditions.isEmpty()) {
                    sqlQuery.append(String.join(" AND ", conditions));
                    conditions.clear();
                }

                sqlQuery.append(" UNION ALL ");

                sqlQuery.append("SELECT 'TOTAL MATERIALES' AS name, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 1, dpd.amount, 0)) AS JAN, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 2, dpd.amount, 0)) AS FEB, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 3, dpd.amount, 0)) AS MAR, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3), dpd.amount, 0)) AS '1/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 4, dpd.amount, 0)) AS APR, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 5, dpd.amount, 0)) AS MAY, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 6, dpd.amount, 0)) AS JUN, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (4, 5, 6), dpd.amount, 0)) AS '2/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 7, dpd.amount, 0)) AS JUL, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 8, dpd.amount, 0)) AS AUGUST, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 9, dpd.amount, 0)) AS SEPT, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (7, 8, 9), dpd.amount, 0)) AS '3/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 10, dpd.amount, 0)) AS OCTUBER, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 11, dpd.amount, 0)) AS NOV, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 12, dpd.amount, 0)) AS DIC, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (10, 11, 12), dpd.amount, 0)) AS '4/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), dpd.amount, 0)) AS 'G. TOTAL', \n"
                        + "    'MATERIAL' AS expense_group \n"
                        + "FROM voucher_quetzales AS vd \n"
                        + "JOIN purchases_vouchers_quetzales_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                        + "JOIN purchases_quetzales pd ON pd.id = pvdv.id_purchase \n"
                        + "JOIN providers pr ON pd.provider = pr.id \n"
                        + "JOIN expense_category c ON pr.category = c.id \n"
                        + "JOIN description_purchases_quetzales dpd ON dpd.purchase = pd.id \n"
                        + "WHERE");

                if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'KCI' GROUP BY expense_group ");
                }

                if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'SQA' GROUP BY expense_group ");
                }

                if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' GROUP BY expense_group ");
                }

                if (!conditions.isEmpty()) {
                    sqlQuery.append(String.join(" AND ", conditions));
                    conditions.clear();
                }

            }

            if (bothRadio.isSelected()) {
                sqlQuery = new StringBuilder(
                        "  SELECT			\n"
                        + "                                                  name,\n"
                        + "                                                  SUM(JAN) AS JAN,\n"
                        + "                                                  SUM(FEB) AS FEB,\n"
                        + "                                                  SUM(MAR) AS MAR,\n"
                        + "                                                  SUM(JAN + FEB + MAR) AS '1/ 4분기 / SUBDIVISION',\n"
                        + "                                                  SUM(APR) AS APR,\n"
                        + "                                                  SUM(MAY) AS MAY,\n"
                        + "                                                  SUM(JUN) AS JUN,\n"
                        + "                                                  SUM(APR  +  MAY + JUN) AS '2/ 4분기 / SUBDIVISION',\n"
                        + "                                                  SUM(JUL) AS JUL,\n"
                        + "                                                  SUM(AUGUST) AS AUGUST,\n"
                        + "                                                  SUM(SEPT) AS SEPT,\n"
                        + "                                                  SUM(JUL + AUGUST + SEPT) AS '3/ 4분기 / SUBDIVISION',\n"
                        + "                                                  SUM(OCTUBER) AS OCTUBER,\n"
                        + "                                                  SUM(NOV) AS NOV,\n"
                        + "                                                  SUM(DIC) AS DIC,\n"
                        + "                                                  SUM(OCTUBER + NOV + DIC) AS '4/ 4분기 / SUBDIVISION',\n"
                        + "                                                  SUM(JAN + FEB + MAR + APR + MAY + JUN + JUL + AUGUST + SEPT + OCTUBER + NOV + DIC) AS 'G. TOTAL',\n"
                        + "                                                  MAX(expense_group) AS expense_group\n"
                        + "                                              FROM (\n"
                        + "                                                  SELECT\n"
                        + "                                                      c.name AS name,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=1, dpd.amount / 7.55, 0)) AS JAN,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=2, dpd.amount / 7.55, 0)) AS FEB,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=3, dpd.amount / 7.55, 0)) AS MAR,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=4, dpd.amount / 7.55, 0)) AS APR,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=5, dpd.amount / 7.55, 0)) AS MAY,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=6, dpd.amount / 7.55, 0)) AS JUN,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=7, dpd.amount / 7.55, 0)) AS JUL,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=8, dpd.amount / 7.55, 0)) AS AUGUST,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=9, dpd.amount / 7.55, 0)) AS SEPT,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=10, dpd.amount / 7.55, 0)) AS OCTUBER,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=11, dpd.amount / 7.55, 0)) AS NOV,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=12, dpd.amount / 7.55, 0)) AS DIC,\n"
                        + "                                                      'FIXED' as expense_group\n"
                        + "                                                  FROM voucher_quetzales AS vd \n"
                        + "                                              JOIN purchases_vouchers_quetzales_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                        + "                                              JOIN purchases_quetzales pd ON pd.id = pvdv.id_purchase \n"
                        + "                                              JOIN providers pr ON pd.provider = pr.id \n"
                        + "                                              JOIN expense_category c ON pr.category = c.id \n"
                        + "                                              JOIN description_purchases_quetzales dpd ON dpd.purchase = pd.id WHERE"
                );
                if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'KCI' GROUP BY c.name ");
                }

                if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'SQA' GROUP BY c.name ");
                }

                if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' GROUP BY c.name ");
                }

                if (!conditions.isEmpty()) {
                    sqlQuery.append(String.join(" AND ", conditions));
                    conditions.clear();
                }

                sqlQuery.append(" UNION ALL ");

                sqlQuery.append(
                        " SELECT\n"
                        + "                                                      c.name AS name,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=1, dpd.amount, 0)) AS JAN,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=2, dpd.amount, 0)) AS FEB,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=3, dpd.amount, 0)) AS MAR,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=4, dpd.amount, 0)) AS APR,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=5, dpd.amount, 0)) AS MAY,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=6, dpd.amount, 0)) AS JUN,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=7, dpd.amount, 0)) AS JUL,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=8, dpd.amount, 0)) AS AUGUST,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=9, dpd.amount, 0)) AS SEPT,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=10, dpd.amount, 0)) AS OCTUBER,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=11, dpd.amount, 0)) AS NOV,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=12, dpd.amount, 0)) AS DIC,\n"
                        + "                                                       'FIXED' as expense_group\n"
                        + "                                                 FROM voucher_dollars AS vd \n"
                        + "                                              JOIN purchases_vouchers_dollars_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                        + "                                              JOIN purchases_dollars pd ON pd.id = pvdv.id_purchase \n"
                        + "                                              JOIN providers pr ON pd.provider = pr.id \n"
                        + "                                              JOIN expense_category c ON pr.category = c.id \n"
                        + "                                              JOIN description_purchases_dollars dpd ON dpd.purchase = pd.id WHERE "
                );

                if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'KCI' GROUP BY c.name ");
                }

                if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'SQA' GROUP BY c.name ");
                }

                if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' GROUP BY c.name ");
                }

                if (!conditions.isEmpty()) {
                    sqlQuery.append(String.join(" AND ", conditions));
                    conditions.clear();
                }

                sqlQuery.append(
                        " ) AS fixed_combine_data_dollars_quetzles\n"
                        + " GROUP BY name "
                );

                sqlQuery.append("UNION ALL");

                sqlQuery.append(
                        "   SELECT			\n"
                        + "                                                  name,\n"
                        + "                                                  SUM(JAN) AS JAN,\n"
                        + "                                                  SUM(FEB) AS FEB,\n"
                        + "                                                  SUM(MAR) AS MAR,\n"
                        + "                                                  SUM(JAN + FEB + MAR) AS '1/ 4분기 / SUBDIVISION',\n"
                        + "                                                  SUM(APR) AS APR,\n"
                        + "                                                  SUM(MAY) AS MAY,\n"
                        + "                                                  SUM(JUN) AS JUN,\n"
                        + "                                                  SUM(APR  +  MAY + JUN) AS '2/ 4분기 / SUBDIVISION',\n"
                        + "                                                  SUM(JUL) AS JUL,\n"
                        + "                                                  SUM(AUGUST) AS AUGUST,\n"
                        + "                                                  SUM(SEPT) AS SEPT,\n"
                        + "                                                  SUM(JUL + AUGUST + SEPT) AS '3/ 4분기 / SUBDIVISION',\n"
                        + "                                                  SUM(OCTUBER) AS OCTUBER,\n"
                        + "                                                  SUM(NOV) AS NOV,\n"
                        + "                                                  SUM(DIC) AS DIC,\n"
                        + "                                                  SUM(OCTUBER + NOV + DIC) AS '4/ 4분기 / SUBDIVISION',\n"
                        + "                                                  SUM(JAN + FEB + MAR + APR + MAY + JUN + JUL + AUGUST + SEPT + OCTUBER + NOV + DIC) AS 'G. TOTAL',\n"
                        + "                                                  MAX(expense_group) AS expense_group\n"
                        + "                                              FROM (\n"
                        + "                                                  SELECT\n"
                        + "                                                      'TOTAL FIJOS' AS name,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=1, dpd.amount / 7.55, 0)) AS JAN,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=2, dpd.amount / 7.55, 0)) AS FEB,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=3, dpd.amount / 7.55, 0)) AS MAR,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=4, dpd.amount / 7.55, 0)) AS APR,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=5, dpd.amount / 7.55, 0)) AS MAY,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=6, dpd.amount / 7.55, 0)) AS JUN,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=7, dpd.amount / 7.55, 0)) AS JUL,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=8, dpd.amount / 7.55, 0)) AS AUGUST,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=9, dpd.amount / 7.55, 0)) AS SEPT,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=10, dpd.amount / 7.55, 0)) AS OCTUBER,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=11, dpd.amount / 7.55, 0)) AS NOV,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=12, dpd.amount / 7.55, 0)) AS DIC,\n"
                        + "                                                      'FIXED' as expense_group\n"
                        + "                                                  FROM voucher_quetzales AS vd \n"
                        + "                                              JOIN purchases_vouchers_quetzales_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                        + "                                              JOIN purchases_quetzales pd ON pd.id = pvdv.id_purchase \n"
                        + "                                              JOIN providers pr ON pd.provider = pr.id \n"
                        + "                                              JOIN expense_category c ON pr.category = c.id \n"
                        + "                                              JOIN description_purchases_quetzales dpd ON dpd.purchase = pd.id WHERE "
                );

                if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'KCI' GROUP BY c.name ");
                }

                if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'SQA' GROUP BY c.name ");
                }

                if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' GROUP BY c.name ");
                }

                if (!conditions.isEmpty()) {
                    sqlQuery.append(String.join(" AND ", conditions));
                    conditions.clear();
                }

                sqlQuery.append(" UNION ALL ");

                sqlQuery.append(
                        "SELECT\n"
                        + "                                                      'TOTAL FIJOS' AS name,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=1, dpd.amount, 0)) AS JAN,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=2, dpd.amount, 0)) AS FEB,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=3, dpd.amount, 0)) AS MAR,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=4, dpd.amount, 0)) AS APR,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=5, dpd.amount, 0)) AS MAY,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=6, dpd.amount, 0)) AS JUN,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=7, dpd.amount, 0)) AS JUL,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=8, dpd.amount, 0)) AS AUGUST,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=9, dpd.amount, 0)) AS SEPT,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=10, dpd.amount, 0)) AS OCTUBER,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=11, dpd.amount, 0)) AS NOV,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=12, dpd.amount, 0)) AS DIC,\n"
                        + "                                                       'FIXED' as expense_group\n"
                        + "                                                 FROM voucher_dollars AS vd \n"
                        + "                                              JOIN purchases_vouchers_dollars_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                        + "                                              JOIN purchases_dollars pd ON pd.id = pvdv.id_purchase \n"
                        + "                                              JOIN providers pr ON pd.provider = pr.id \n"
                        + "                                              JOIN expense_category c ON pr.category = c.id \n"
                        + "                                              JOIN description_purchases_dollars dpd ON dpd.purchase = pd.id WHERE "
                );

                if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'KCI' GROUP BY c.name ");
                }

                if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'SQA' GROUP BY c.name ");
                }

                if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' GROUP BY c.name ");
                }

                if (!conditions.isEmpty()) {
                    sqlQuery.append(String.join(" AND ", conditions));
                    conditions.clear();
                }

                sqlQuery.append(" ) AS fixed_combine_data_dollars_quetzles\n"
                        + "  GROUP BY name");
                /**
                 * Fin consulta a gastos fijos y agrupacion de totales para
                 * gastos fijos
                 */

                /**
                 * UNION ALL GET MATERIAL EXPENSE
                 */
                sqlQuery.append(" UNION ALL");

                sqlQuery.append(
                        "   SELECT			\n"
                        + "                                                  name,\n"
                        + "                                                  SUM(JAN) AS JAN,\n"
                        + "                                                  SUM(FEB) AS FEB,\n"
                        + "                                                  SUM(MAR) AS MAR,\n"
                        + "                                                  SUM(JAN + FEB + MAR) AS '1/ 4분기 / SUBDIVISION',\n"
                        + "                                                  SUM(APR) AS APR,\n"
                        + "                                                  SUM(MAY) AS MAY,\n"
                        + "                                                  SUM(JUN) AS JUN,\n"
                        + "                                                  SUM(APR  +  MAY + JUN) AS '2/ 4분기 / SUBDIVISION',\n"
                        + "                                                  SUM(JUL) AS JUL,\n"
                        + "                                                  SUM(AUGUST) AS AUGUST,\n"
                        + "                                                  SUM(SEPT) AS SEPT,\n"
                        + "                                                  SUM(JUL + AUGUST + SEPT) AS '3/ 4분기 / SUBDIVISION',\n"
                        + "                                                  SUM(OCTUBER) AS OCTUBER,\n"
                        + "                                                  SUM(NOV) AS NOV,\n"
                        + "                                                  SUM(DIC) AS DIC,\n"
                        + "                                                  SUM(OCTUBER + NOV + DIC) AS '4/ 4분기 / SUBDIVISION',\n"
                        + "                                                  SUM(JAN + FEB + MAR + APR + MAY + JUN + JUL + AUGUST + SEPT + OCTUBER + NOV + DIC) AS 'G. TOTAL',\n"
                        + "                                                  MAX(expense_group) AS expense_group\n"
                        + "                                              FROM (\n"
                        + "                                                  SELECT\n"
                        + "                                                      c.name AS name,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=1, dpd.amount / 7.55, 0)) AS JAN,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=2, dpd.amount / 7.55, 0)) AS FEB,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=3, dpd.amount / 7.55, 0)) AS MAR,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=4, dpd.amount / 7.55, 0)) AS APR,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=5, dpd.amount / 7.55, 0)) AS MAY,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=6, dpd.amount / 7.55, 0)) AS JUN,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=7, dpd.amount / 7.55, 0)) AS JUL,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=8, dpd.amount / 7.55, 0)) AS AUGUST,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=9, dpd.amount / 7.55, 0)) AS SEPT,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=10, dpd.amount / 7.55, 0)) AS OCTUBER,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=11, dpd.amount / 7.55, 0)) AS NOV,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=12, dpd.amount / 7.55, 0)) AS DIC,\n"
                        + "                                                      'MATERIAL' as expense_group\n"
                        + "                                                FROM voucher_quetzales AS vd \n"
                        + "                         JOIN purchases_vouchers_quetzales_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                        + "                         JOIN purchases_quetzales pd ON pd.id = pvdv.id_purchase \n"
                        + "                         JOIN providers pr ON pd.provider = pr.id \n"
                        + "                         JOIN expense_category c ON pr.category = c.id \n"
                        + "                         JOIN description_purchases_quetzales dpd ON dpd.purchase = pd.id \n"
                        + "                         WHERE "
                );

                if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'KCI' GROUP BY c.name ");
                }

                if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'SQA' GROUP BY c.name ");
                }

                if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' GROUP BY c.name ");
                }

                if (!conditions.isEmpty()) {
                    sqlQuery.append(String.join(" AND ", conditions));
                    conditions.clear();
                }

                sqlQuery.append(" UNION ALL ");

                sqlQuery.append(
                        "  SELECT\n"
                        + "                                                      c.name AS name,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=1, dpd.amount, 0)) AS JAN,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=2, dpd.amount, 0)) AS FEB,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=3, dpd.amount, 0)) AS MAR,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=4, dpd.amount, 0)) AS APR,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=5, dpd.amount, 0)) AS MAY,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=6, dpd.amount, 0)) AS JUN,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=7, dpd.amount, 0)) AS JUL,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=8, dpd.amount, 0)) AS AUGUST,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=9, dpd.amount, 0)) AS SEPT,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=10, dpd.amount, 0)) AS OCTUBER,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=11, dpd.amount, 0)) AS NOV,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=12, dpd.amount, 0)) AS DIC,\n"
                        + "                                                       'MATERIAL' as expense_group\n"
                        + "                                                 FROM voucher_dollars AS vd \n"
                        + "                         JOIN purchases_vouchers_dollars_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                        + "                         JOIN purchases_dollars pd ON pd.id = pvdv.id_purchase \n"
                        + "                         JOIN providers pr ON pd.provider = pr.id \n"
                        + "                         JOIN expense_category c ON pr.category = c.id \n"
                        + "                         JOIN description_purchases_dollars dpd ON dpd.purchase = pd.id \n"
                        + "                         WHERE "
                );

                if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'KCI' GROUP BY c.name ");
                }

                if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'SQA' GROUP BY c.name ");
                }

                if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' GROUP BY c.name ");
                }

                if (!conditions.isEmpty()) {
                    sqlQuery.append(String.join(" AND ", conditions));
                    conditions.clear();
                }

                sqlQuery.append(") AS combined_data\n"
                        + "  GROUP BY name");

                sqlQuery.append(" UNION ALL ");
                /**
                 * MATERIAL EXPENSE GROUP TOTAL
                 */

                sqlQuery.append(
                        " SELECT			\n"
                        + "                                                  name,\n"
                        + "                                                  SUM(JAN) AS JAN,\n"
                        + "                                                  SUM(FEB) AS FEB,\n"
                        + "                                                  SUM(MAR) AS MAR,\n"
                        + "                                                  SUM(JAN + FEB + MAR) AS '1/ 4분기 / SUBDIVISION',\n"
                        + "                                                  SUM(APR) AS APR,\n"
                        + "                                                  SUM(MAY) AS MAY,\n"
                        + "                                                  SUM(JUN) AS JUN,\n"
                        + "                                                  SUM(APR  +  MAY + JUN) AS '2/ 4분기 / SUBDIVISION',\n"
                        + "                                                  SUM(JUL) AS JUL,\n"
                        + "                                                  SUM(AUGUST) AS AUGUST,\n"
                        + "                                                  SUM(SEPT) AS SEPT,\n"
                        + "                                                  SUM(JUL + AUGUST + SEPT) AS '3/ 4분기 / SUBDIVISION',\n"
                        + "                                                  SUM(OCTUBER) AS OCTUBER,\n"
                        + "                                                  SUM(NOV) AS NOV,\n"
                        + "                                                  SUM(DIC) AS DIC,\n"
                        + "                                                  SUM(OCTUBER + NOV + DIC) AS '4/ 4분기 / SUBDIVISION',\n"
                        + "                                                  SUM(JAN + FEB + MAR + APR + MAY + JUN + JUL + AUGUST + SEPT + OCTUBER + NOV + DIC) AS 'G. TOTAL',\n"
                        + "                                                  MAX(expense_group) AS expense_group\n"
                        + "                                              FROM (\n"
                        + "                                                  SELECT\n"
                        + "                                                      'TOTAL MATERIALES' AS name,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=1, dpd.amount / 7.55, 0)) AS JAN,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=2, dpd.amount / 7.55, 0)) AS FEB,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=3, dpd.amount / 7.55, 0)) AS MAR,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=4, dpd.amount / 7.55, 0)) AS APR,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=5, dpd.amount / 7.55, 0)) AS MAY,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=6, dpd.amount / 7.55, 0)) AS JUN,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=7, dpd.amount / 7.55, 0)) AS JUL,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=8, dpd.amount / 7.55, 0)) AS AUGUST,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=9, dpd.amount / 7.55, 0)) AS SEPT,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=10, dpd.amount / 7.55, 0)) AS OCTUBER,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=11, dpd.amount / 7.55, 0)) AS NOV,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=12, dpd.amount / 7.55, 0)) AS DIC,\n"
                        + "                                                      'MATERIAL' as expense_group\n"
                        + "                                                FROM voucher_quetzales AS vd \n"
                        + "                         JOIN purchases_vouchers_quetzales_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                        + "                         JOIN purchases_quetzales pd ON pd.id = pvdv.id_purchase \n"
                        + "                         JOIN providers pr ON pd.provider = pr.id \n"
                        + "                         JOIN expense_category c ON pr.category = c.id \n"
                        + "                         JOIN description_purchases_quetzales dpd ON dpd.purchase = pd.id \n"
                        + "                         WHERE "
                );

                if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'KCI' GROUP BY c.name ");
                }

                if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'SQA' GROUP BY c.name ");
                }

                if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' GROUP BY c.name ");
                }

                if (!conditions.isEmpty()) {
                    sqlQuery.append(String.join(" AND ", conditions));
                    conditions.clear();
                }

                sqlQuery.append(" UNION ALL ");

                sqlQuery.append(
                        "  SELECT\n"
                        + "                                                      'TOTAL MATERIALES' AS name,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=1, dpd.amount, 0)) AS JAN,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=2, dpd.amount, 0)) AS FEB,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=3, dpd.amount, 0)) AS MAR,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=4, dpd.amount, 0)) AS APR,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=5, dpd.amount, 0)) AS MAY,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=6, dpd.amount, 0)) AS JUN,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=7, dpd.amount, 0)) AS JUL,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=8, dpd.amount, 0)) AS AUGUST,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=9, dpd.amount, 0)) AS SEPT,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=10, dpd.amount, 0)) AS OCTUBER,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=11, dpd.amount, 0)) AS NOV,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=12, dpd.amount, 0)) AS DIC,\n"
                        + "                                                       'MATERIAL' as expense_group\n"
                        + "                                                 FROM voucher_dollars AS vd \n"
                        + "                         JOIN purchases_vouchers_dollars_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                        + "                         JOIN purchases_dollars pd ON pd.id = pvdv.id_purchase \n"
                        + "                         JOIN providers pr ON pd.provider = pr.id \n"
                        + "                         JOIN expense_category c ON pr.category = c.id \n"
                        + "                         JOIN description_purchases_dollars dpd ON dpd.purchase = pd.id \n"
                        + "                         WHERE "
                );

                if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'KCI' GROUP BY c.name ");
                }

                if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'SQA' GROUP BY c.name ");
                }

                if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' GROUP BY c.name ");
                }

                if (!conditions.isEmpty()) {
                    sqlQuery.append(String.join(" AND ", conditions));
                    conditions.clear();
                }

                sqlQuery.append(
                        " ) AS combined_data\n"
                        + "                      GROUP BY name "
                );

                if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    sqlQuery.append("UNION ALL ");
                    sqlQuery.append("SELECT 'BUYERS BILLING' as name,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) = 1, bb.amount, 0)) AS JAN,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) = 2, bb.amount, 0)) AS FEB,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) = 3, bb.amount, 0)) AS MAR,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) IN (1, 2, 3), bb.amount, 0)) AS '1/ 4분기 / SUBDIVISION',\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) = 4, bb.amount, 0)) AS APR,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) = 5, bb.amount, 0)) AS MAY,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) = 6, bb.amount, 0)) AS JUN,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) IN (4, 5, 6), bb.amount, 0)) AS '2/ 4분기 / SUBDIVISION',\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) = 7, bb.amount, 0)) AS JUL,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) = 8, bb.amount, 0)) AS AUGUST,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) = 9, bb.amount, 0)) AS SEPT,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) IN (7, 8, 9), bb.amount, 0)) AS '3/ 4분기 / SUBDIVISION',\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) = 10, bb.amount, 0)) AS OCTUBER,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) = 11, bb.amount, 0)) AS NOV,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) = 12, bb.amount, 0)) AS DIC,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) IN (10, 11, 12), bb.amount, 0)) AS '4/ 4분기 / SUBDIVISION',\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), bb.amount, 0)) AS 'G. TOTAL',\n"
                            + "    'SALES' AS expense_group\n"
                            + "FROM billing_buyers AS BB\n"
                            + "WHERE YEAR(bb.emissionDate) = ? \n"
                            + "GROUP BY expense_group");
                }

            }

        }

        /**
         * Generate report for providers
         */
        if (filterPerProviderButton.isSelected()) {
            sqlQuery = new StringBuilder("SELECT \n"
                    + "                     pr.name AS name, \n"
                    + "                     SUM(IF(MONTH(vd.payment_date) = 1, dpd.amount, 0)) AS JAN, \n"
                    + "                     SUM(IF(MONTH(vd.payment_date) = 2, dpd.amount, 0)) AS FEB, \n"
                    + "                     SUM(IF(MONTH(vd.payment_date) = 3, dpd.amount, 0)) AS MAR, \n"
                    + "                     SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3), dpd.amount, 0)) AS '1/ 4분기 / SUBDIVISION',\n"
                    + "                     SUM(IF(MONTH(vd.payment_date) = 4, dpd.amount, 0)) AS APR,\n"
                    + "                     SUM(IF(MONTH(vd.payment_date) = 5, dpd.amount, 0)) AS MAY,\n"
                    + "                     SUM(IF(MONTH(vd.payment_date) = 6, dpd.amount, 0)) AS JUN,\n"
                    + "                     SUM(IF(MONTH(vd.payment_date) IN (4, 5, 6), dpd.amount, 0)) AS '2/ 4분기 / SUBDIVISION',\n"
                    + "                     SUM(IF(MONTH(vd.payment_date) = 7, dpd.amount, 0)) AS JUL,\n"
                    + "                     SUM(IF(MONTH(vd.payment_date) = 8, dpd.amount, 0)) AS AUGUST,\n"
                    + "                     SUM(IF(MONTH(vd.payment_date) = 9, dpd.amount, 0)) AS SEPT,\n"
                    + "                     SUM(IF(MONTH(vd.payment_date) IN (7, 8, 9), dpd.amount, 0)) AS '3/ 4분기 / SUBDIVISION',\n"
                    + "                     SUM(IF(MONTH(vd.payment_date) = 10, dpd.amount, 0)) AS OCTUBER,\n"
                    + "                     SUM(IF(MONTH(vd.payment_date) = 11, dpd.amount, 0)) AS NOV,\n"
                    + "                     SUM(IF(MONTH(vd.payment_date) = 12, dpd.amount, 0)) AS DIC,\n"
                    + "                     SUM(IF(MONTH(vd.payment_date) IN (10, 11, 12), dpd.amount, 0)) AS '4/ 4분기 / SUBDIVISION', \n"
                    + "                     SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), dpd.amount, 0)) AS 'G. TOTAL', \n"
                    + "                     'FIXED' AS expense_group \n"
                    + "                     FROM voucher_dollars AS vd \n"
                    + "                     JOIN purchases_vouchers_dollars_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                    + "                     JOIN purchases_dollars pd ON pd.id = pvdv.id_purchase \n"
                    + "                     JOIN providers pr ON pd.provider = pr.id \n"
                    + "                     JOIN expense_category c ON pr.category = c.id \n"
                    + "                     JOIN description_purchases_dollars dpd ON dpd.purchase = pd.id WHERE \n");

            if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'KCI' GROUP BY pr.name ");
            }

            if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'SQA' GROUP BY pr.name ");
            }

            if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' GROUP BY pr.name ");
            }

            if (!conditions.isEmpty()) {
                sqlQuery.append(String.join(" AND ", conditions));
                conditions.clear();
            }

            sqlQuery.append(" UNION ALL ");

            sqlQuery.append("SELECT 'TOTAL FIJOS' AS name, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) = 1, dpd.amount, 0)) AS JAN, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) = 2, dpd.amount, 0)) AS FEB, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) = 3, dpd.amount, 0)) AS MAR, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3), dpd.amount, 0)) AS '1/ 4분기 / SUBDIVISION', \n"
                    + "    SUM(IF(MONTH(vd.payment_date) = 4, dpd.amount, 0)) AS APR, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) = 5, dpd.amount, 0)) AS MAY, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) = 6, dpd.amount, 0)) AS JUN, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) IN (4, 5, 6), dpd.amount, 0)) AS '2/ 4분기 / SUBDIVISION', \n"
                    + "    SUM(IF(MONTH(vd.payment_date) = 7, dpd.amount, 0)) AS JUL, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) = 8, dpd.amount, 0)) AS AUGUST, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) = 9, dpd.amount, 0)) AS SEPT, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) IN (7, 8, 9), dpd.amount, 0)) AS '3/ 4분기 / SUBDIVISION', \n"
                    + "    SUM(IF(MONTH(vd.payment_date) = 10, dpd.amount, 0)) AS OCTUBER, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) = 11, dpd.amount, 0)) AS NOV, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) = 12, dpd.amount, 0)) AS DIC, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) IN (10, 11, 12), dpd.amount, 0)) AS '4/ 4분기 / SUBDIVISION', \n"
                    + "    SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), dpd.amount, 0)) AS 'G. TOTAL', \n"
                    + "    'FIXED' AS expense_group \n"
                    + "FROM voucher_dollars AS vd \n"
                    + "JOIN purchases_vouchers_dollars_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                    + "JOIN purchases_dollars pd ON pd.id = pvdv.id_purchase \n"
                    + "JOIN providers pr ON pd.provider = pr.id \n"
                    + "JOIN expense_category c ON pr.category = c.id \n"
                    + "JOIN description_purchases_dollars dpd ON dpd.purchase = pd.id \n"
                    + "WHERE");

            if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'KCI' GROUP BY expense_group ");
            }

            if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'SQA' GROUP BY expense_group ");
            }

            if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' GROUP BY expense_group ");
            }

            if (!conditions.isEmpty()) {
                sqlQuery.append(String.join(" AND ", conditions));
                conditions.clear();
            }

            sqlQuery.append(" UNION ALL ");

            sqlQuery.append("SELECT pr.name AS name, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) = 1, dpd.amount, 0)) AS JAN, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) = 2, dpd.amount, 0)) AS FEB, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) = 3, dpd.amount, 0)) AS MAR, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3), dpd.amount, 0)) AS '1/ 4분기 / SUBDIVISION', \n"
                    + "    SUM(IF(MONTH(vd.payment_date) = 4, dpd.amount, 0)) AS APR, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) = 5, dpd.amount, 0)) AS MAY, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) = 6, dpd.amount, 0)) AS JUN, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) IN (4, 5, 6), dpd.amount, 0)) AS '2/ 4분기 / SUBDIVISION', \n"
                    + "    SUM(IF(MONTH(vd.payment_date) = 7, dpd.amount, 0)) AS JUL, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) = 8, dpd.amount, 0)) AS AUGUST, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) = 9, dpd.amount, 0)) AS SEPT, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) IN (7, 8, 9), dpd.amount, 0)) AS '3/ 4분기 / SUBDIVISION', \n"
                    + "    SUM(IF(MONTH(vd.payment_date) = 10, dpd.amount, 0)) AS OCTUBER, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) = 11, dpd.amount, 0)) AS NOV, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) = 12, dpd.amount, 0)) AS DIC, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) IN (10, 11, 12), dpd.amount, 0)) AS '4/ 4분기 / SUBDIVISION', \n"
                    + "    SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), dpd.amount, 0)) AS 'G. TOTAL', \n"
                    + "    'MATERIAL' AS expense_group \n"
                    + "FROM voucher_dollars AS vd \n"
                    + "JOIN purchases_vouchers_dollars_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                    + "JOIN purchases_dollars pd ON pd.id = pvdv.id_purchase \n"
                    + "JOIN providers pr ON pd.provider = pr.id \n"
                    + "JOIN expense_category c ON pr.category = c.id \n"
                    + "JOIN description_purchases_dollars dpd ON dpd.purchase = pd.id \n"
                    + "WHERE");

            if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'KCI' GROUP BY pr.name ");
            }

            if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'SQA' GROUP BY pr.name ");
            }

            if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' GROUP BY pr.name ");
            }

            if (!conditions.isEmpty()) {
                sqlQuery.append(String.join(" AND ", conditions));
                conditions.clear();
            }

            sqlQuery.append(" UNION ALL ");

            sqlQuery.append("SELECT 'TOTAL MATERIALES' AS name, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) = 1, dpd.amount, 0)) AS JAN, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) = 2, dpd.amount, 0)) AS FEB, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) = 3, dpd.amount, 0)) AS MAR, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3), dpd.amount, 0)) AS '1/ 4분기 / SUBDIVISION', \n"
                    + "    SUM(IF(MONTH(vd.payment_date) = 4, dpd.amount, 0)) AS APR, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) = 5, dpd.amount, 0)) AS MAY, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) = 6, dpd.amount, 0)) AS JUN, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) IN (4, 5, 6), dpd.amount, 0)) AS '2/ 4분기 / SUBDIVISION', \n"
                    + "    SUM(IF(MONTH(vd.payment_date) = 7, dpd.amount, 0)) AS JUL, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) = 8, dpd.amount, 0)) AS AUGUST, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) = 9, dpd.amount, 0)) AS SEPT, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) IN (7, 8, 9), dpd.amount, 0)) AS '3/ 4분기 / SUBDIVISION', \n"
                    + "    SUM(IF(MONTH(vd.payment_date) = 10, dpd.amount, 0)) AS OCTUBER, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) = 11, dpd.amount, 0)) AS NOV, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) = 12, dpd.amount, 0)) AS DIC, \n"
                    + "    SUM(IF(MONTH(vd.payment_date) IN (10, 11, 12), dpd.amount, 0)) AS '4/ 4분기 / SUBDIVISION', \n"
                    + "    SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), dpd.amount, 0)) AS 'G. TOTAL', \n"
                    + "    'MATERIAL' AS expense_group \n"
                    + "FROM voucher_dollars AS vd \n"
                    + "JOIN purchases_vouchers_dollars_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                    + "JOIN purchases_dollars pd ON pd.id = pvdv.id_purchase \n"
                    + "JOIN providers pr ON pd.provider = pr.id \n"
                    + "JOIN expense_category c ON pr.category = c.id \n"
                    + "JOIN description_purchases_dollars dpd ON dpd.purchase = pd.id \n"
                    + "WHERE");

            if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'KCI' GROUP BY expense_group ");
            }

            if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'SQA' GROUP BY expense_group ");
            }

            if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' GROUP BY expense_group ");
            }

            if (!conditions.isEmpty()) {
                sqlQuery.append(String.join(" AND ", conditions));
                conditions.clear();
            }

            if (apparelKCICheckBox.isSelected()) {
                sqlQuery.append(" UNION ALL ");

                sqlQuery.append("SELECT 'BUYERS BILLING' as name,\n"
                        + "    SUM(IF(MONTH(bb.emissionDate) = 1, bb.amount, 0)) AS JAN,\n"
                        + "    SUM(IF(MONTH(bb.emissionDate) = 2, bb.amount, 0)) AS FEB,\n"
                        + "    SUM(IF(MONTH(bb.emissionDate) = 3, bb.amount, 0)) AS MAR,\n"
                        + "    SUM(IF(MONTH(bb.emissionDate) IN (1, 2, 3), bb.amount, 0)) AS '1/ 4분기 / SUBDIVISION',\n"
                        + "    SUM(IF(MONTH(bb.emissionDate) = 4, bb.amount, 0)) AS APR,\n"
                        + "    SUM(IF(MONTH(bb.emissionDate) = 5, bb.amount, 0)) AS MAY,\n"
                        + "    SUM(IF(MONTH(bb.emissionDate) = 6, bb.amount, 0)) AS JUN,\n"
                        + "    SUM(IF(MONTH(bb.emissionDate) IN (4, 5, 6), bb.amount, 0)) AS '2/ 4분기 / SUBDIVISION',\n"
                        + "    SUM(IF(MONTH(bb.emissionDate) = 7, bb.amount, 0)) AS JUL,\n"
                        + "    SUM(IF(MONTH(bb.emissionDate) = 8, bb.amount, 0)) AS AUGUST,\n"
                        + "    SUM(IF(MONTH(bb.emissionDate) = 9, bb.amount, 0)) AS SEPT,\n"
                        + "    SUM(IF(MONTH(bb.emissionDate) IN (7, 8, 9), bb.amount, 0)) AS '3/ 4분기 / SUBDIVISION',\n"
                        + "    SUM(IF(MONTH(bb.emissionDate) = 10, bb.amount, 0)) AS OCTUBER,\n"
                        + "    SUM(IF(MONTH(bb.emissionDate) = 11, bb.amount, 0)) AS NOV,\n"
                        + "    SUM(IF(MONTH(bb.emissionDate) = 12, bb.amount, 0)) AS DIC,\n"
                        + "    SUM(IF(MONTH(bb.emissionDate) IN (10, 11, 12), bb.amount, 0)) AS '4/ 4분기 / SUBDIVISION',\n"
                        + "    SUM(IF(MONTH(bb.emissionDate) IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), bb.amount, 0)) AS 'G. TOTAL',\n"
                        + "    'SALES' AS expense_group\n"
                        + "FROM billing_buyers AS BB\n"
                        + "WHERE YEAR(bb.emissionDate) = ? \n"
                        + "GROUP BY expense_group");

            }

            if (quetzalesRadio.isSelected()) {

                sqlQuery = new StringBuilder("SELECT \n"
                        + "                     pr.name AS name, \n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 1, dpd.amount, 0)) AS JAN, \n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 2, dpd.amount, 0)) AS FEB, \n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 3, dpd.amount, 0)) AS MAR, \n"
                        + "                     SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3), dpd.amount, 0)) AS '1/ 4분기 / SUBDIVISION',\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 4, dpd.amount, 0)) AS APR,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 5, dpd.amount, 0)) AS MAY,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 6, dpd.amount, 0)) AS JUN,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) IN (4, 5, 6), dpd.amount, 0)) AS '2/ 4분기 / SUBDIVISION',\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 7, dpd.amount, 0)) AS JUL,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 8, dpd.amount, 0)) AS AUGUST,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 9, dpd.amount, 0)) AS SEPT,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) IN (7, 8, 9), dpd.amount, 0)) AS '3/ 4분기 / SUBDIVISION',\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 10, dpd.amount, 0)) AS OCTUBER,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 11, dpd.amount, 0)) AS NOV,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 12, dpd.amount, 0)) AS DIC,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) IN (10, 11, 12), dpd.amount, 0)) AS '4/ 4분기 / SUBDIVISION', \n"
                        + "                     SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), dpd.amount, 0)) AS 'G. TOTAL', \n"
                        + "                     'FIXED' AS expense_group \n"
                        + "                     FROM voucher_quetzales AS vd \n"
                        + "                     JOIN purchases_vouchers_quetzales_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                        + "                     JOIN purchases_quetzales pd ON pd.id = pvdv.id_purchase \n"
                        + "                     JOIN providers pr ON pd.provider = pr.id \n"
                        + "                     JOIN expense_category c ON pr.category = c.id \n"
                        + "                     JOIN description_purchases_quetzales dpd ON dpd.purchase = pd.id WHERE \n");

                if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'KCI' GROUP BY pr.name ");
                }

                if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'SQA' GROUP BY pr.name ");
                }

                if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' GROUP BY pr.name ");
                }

                if (!conditions.isEmpty()) {
                    sqlQuery.append(String.join(" AND ", conditions));
                    conditions.clear();
                }

                sqlQuery.append(" UNION ALL ");

                sqlQuery.append("SELECT 'TOTAL FIJOS' AS name, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 1, dpd.amount, 0)) AS JAN, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 2, dpd.amount, 0)) AS FEB, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 3, dpd.amount, 0)) AS MAR, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3), dpd.amount, 0)) AS '1/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 4, dpd.amount, 0)) AS APR, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 5, dpd.amount, 0)) AS MAY, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 6, dpd.amount, 0)) AS JUN, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (4, 5, 6), dpd.amount, 0)) AS '2/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 7, dpd.amount, 0)) AS JUL, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 8, dpd.amount, 0)) AS AUGUST, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 9, dpd.amount, 0)) AS SEPT, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (7, 8, 9), dpd.amount, 0)) AS '3/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 10, dpd.amount, 0)) AS OCTUBER, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 11, dpd.amount, 0)) AS NOV, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 12, dpd.amount, 0)) AS DIC, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (10, 11, 12), dpd.amount, 0)) AS '4/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), dpd.amount, 0)) AS 'G. TOTAL', \n"
                        + "    'FIXED' AS expense_group \n"
                        + "FROM voucher_quetzales AS vd \n"
                        + "JOIN purchases_vouchers_quetzales_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                        + "JOIN purchases_quetzales pd ON pd.id = pvdv.id_purchase \n"
                        + "JOIN providers pr ON pd.provider = pr.id \n"
                        + "JOIN expense_category c ON pr.category = c.id \n"
                        + "JOIN description_purchases_quetzales dpd ON dpd.purchase = pd.id \n"
                        + "WHERE");

                if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'KCI' GROUP BY expense_group ");
                }

                if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'SQA' GROUP BY expense_group ");
                }

                if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' GROUP BY expense_group ");
                }

                if (!conditions.isEmpty()) {
                    sqlQuery.append(String.join(" AND ", conditions));
                    conditions.clear();
                }

                sqlQuery.append(" UNION ALL ");

                sqlQuery.append("SELECT pr.name AS name, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 1, dpd.amount, 0)) AS JAN, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 2, dpd.amount, 0)) AS FEB, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 3, dpd.amount, 0)) AS MAR, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3), dpd.amount, 0)) AS '1/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 4, dpd.amount, 0)) AS APR, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 5, dpd.amount, 0)) AS MAY, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 6, dpd.amount, 0)) AS JUN, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (4, 5, 6), dpd.amount, 0)) AS '2/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 7, dpd.amount, 0)) AS JUL, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 8, dpd.amount, 0)) AS AUGUST, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 9, dpd.amount, 0)) AS SEPT, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (7, 8, 9), dpd.amount, 0)) AS '3/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 10, dpd.amount, 0)) AS OCTUBER, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 11, dpd.amount, 0)) AS NOV, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 12, dpd.amount, 0)) AS DIC, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (10, 11, 12), dpd.amount, 0)) AS '4/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), dpd.amount, 0)) AS 'G. TOTAL', \n"
                        + "    'MATERIAL' AS expense_group \n"
                        + "FROM voucher_quetzales AS vd \n"
                        + "JOIN purchases_vouchers_quetzales_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                        + "JOIN purchases_quetzales pd ON pd.id = pvdv.id_purchase \n"
                        + "JOIN providers pr ON pd.provider = pr.id \n"
                        + "JOIN expense_category c ON pr.category = c.id \n"
                        + "JOIN description_purchases_quetzales dpd ON dpd.purchase = pd.id \n"
                        + "WHERE");

                if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'KCI' GROUP BY pr.name ");
                }

                if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'SQA' GROUP BY pr.name ");
                }

                if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' GROUP BY pr.name ");
                }

                if (!conditions.isEmpty()) {
                    sqlQuery.append(String.join(" AND ", conditions));
                    conditions.clear();
                }

                sqlQuery.append(" UNION ALL ");

                sqlQuery.append("SELECT 'TOTAL MATERIALES' AS name, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 1, dpd.amount, 0)) AS JAN, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 2, dpd.amount, 0)) AS FEB, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 3, dpd.amount, 0)) AS MAR, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3), dpd.amount, 0)) AS '1/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 4, dpd.amount, 0)) AS APR, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 5, dpd.amount, 0)) AS MAY, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 6, dpd.amount, 0)) AS JUN, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (4, 5, 6), dpd.amount, 0)) AS '2/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 7, dpd.amount, 0)) AS JUL, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 8, dpd.amount, 0)) AS AUGUST, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 9, dpd.amount, 0)) AS SEPT, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (7, 8, 9), dpd.amount, 0)) AS '3/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 10, dpd.amount, 0)) AS OCTUBER, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 11, dpd.amount, 0)) AS NOV, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 12, dpd.amount, 0)) AS DIC, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (10, 11, 12), dpd.amount, 0)) AS '4/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), dpd.amount, 0)) AS 'G. TOTAL', \n"
                        + "    'MATERIAL' AS expense_group \n"
                        + "FROM voucher_quetzales AS vd \n"
                        + "JOIN purchases_vouchers_quetzales_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                        + "JOIN purchases_quetzales pd ON pd.id = pvdv.id_purchase \n"
                        + "JOIN providers pr ON pd.provider = pr.id \n"
                        + "JOIN expense_category c ON pr.category = c.id \n"
                        + "JOIN description_purchases_quetzales dpd ON dpd.purchase = pd.id \n"
                        + "WHERE");

                if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'KCI' GROUP BY expense_group ");
                }

                if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'SQA' GROUP BY expense_group ");
                }

                if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' GROUP BY expense_group ");
                }

                if (!conditions.isEmpty()) {
                    sqlQuery.append(String.join(" AND ", conditions));
                    conditions.clear();
                }

            }

            if (quetzalesRadio.isSelected()) {

                sqlQuery = new StringBuilder("SELECT \n"
                        + "                     pr.name AS name, \n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 1, dpd.amount, 0)) AS JAN, \n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 2, dpd.amount, 0)) AS FEB, \n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 3, dpd.amount, 0)) AS MAR, \n"
                        + "                     SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3), dpd.amount, 0)) AS '1/ 4분기 / SUBDIVISION',\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 4, dpd.amount, 0)) AS APR,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 5, dpd.amount, 0)) AS MAY,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 6, dpd.amount, 0)) AS JUN,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) IN (4, 5, 6), dpd.amount, 0)) AS '2/ 4분기 / SUBDIVISION',\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 7, dpd.amount, 0)) AS JUL,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 8, dpd.amount, 0)) AS AUGUST,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 9, dpd.amount, 0)) AS SEPT,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) IN (7, 8, 9), dpd.amount, 0)) AS '3/ 4분기 / SUBDIVISION',\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 10, dpd.amount, 0)) AS OCTUBER,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 11, dpd.amount, 0)) AS NOV,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) = 12, dpd.amount, 0)) AS DIC,\n"
                        + "                     SUM(IF(MONTH(vd.payment_date) IN (10, 11, 12), dpd.amount, 0)) AS '4/ 4분기 / SUBDIVISION', \n"
                        + "                     SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), dpd.amount, 0)) AS 'G. TOTAL', \n"
                        + "                     'FIXED' AS expense_group \n"
                        + "                     FROM voucher_quetzales AS vd \n"
                        + "                     JOIN purchases_vouchers_quetzales_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                        + "                     JOIN purchases_quetzales pd ON pd.id = pvdv.id_purchase \n"
                        + "                     JOIN providers pr ON pd.provider = pr.id \n"
                        + "                     JOIN expense_category c ON pr.category = c.id \n"
                        + "                     JOIN description_purchases_quetzales dpd ON dpd.purchase = pd.id WHERE \n");

                if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'KCI' GROUP BY pr.name ");
                }

                if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'SQA' GROUP BY pr.name ");
                }

                if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' GROUP BY pr.name ");
                }

                if (!conditions.isEmpty()) {
                    sqlQuery.append(String.join(" AND ", conditions));
                    conditions.clear();
                }

                sqlQuery.append(" UNION ALL ");

                sqlQuery.append("SELECT 'TOTAL FIJOS' AS name, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 1, dpd.amount, 0)) AS JAN, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 2, dpd.amount, 0)) AS FEB, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 3, dpd.amount, 0)) AS MAR, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3), dpd.amount, 0)) AS '1/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 4, dpd.amount, 0)) AS APR, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 5, dpd.amount, 0)) AS MAY, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 6, dpd.amount, 0)) AS JUN, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (4, 5, 6), dpd.amount, 0)) AS '2/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 7, dpd.amount, 0)) AS JUL, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 8, dpd.amount, 0)) AS AUGUST, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 9, dpd.amount, 0)) AS SEPT, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (7, 8, 9), dpd.amount, 0)) AS '3/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 10, dpd.amount, 0)) AS OCTUBER, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 11, dpd.amount, 0)) AS NOV, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 12, dpd.amount, 0)) AS DIC, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (10, 11, 12), dpd.amount, 0)) AS '4/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), dpd.amount, 0)) AS 'G. TOTAL', \n"
                        + "    'FIXED' AS expense_group \n"
                        + "FROM voucher_quetzales AS vd \n"
                        + "JOIN purchases_vouchers_quetzales_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                        + "JOIN purchases_quetzales pd ON pd.id = pvdv.id_purchase \n"
                        + "JOIN providers pr ON pd.provider = pr.id \n"
                        + "JOIN expense_category c ON pr.category = c.id \n"
                        + "JOIN description_purchases_quetzales dpd ON dpd.purchase = pd.id \n"
                        + "WHERE");

                if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'KCI' GROUP BY expense_group ");
                }

                if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'SQA' GROUP BY expense_group ");
                }

                if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' GROUP BY expense_group ");
                }

                if (!conditions.isEmpty()) {
                    sqlQuery.append(String.join(" AND ", conditions));
                    conditions.clear();
                }

                sqlQuery.append(" UNION ALL ");

                sqlQuery.append("SELECT pr.name AS name, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 1, dpd.amount, 0)) AS JAN, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 2, dpd.amount, 0)) AS FEB, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 3, dpd.amount, 0)) AS MAR, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3), dpd.amount, 0)) AS '1/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 4, dpd.amount, 0)) AS APR, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 5, dpd.amount, 0)) AS MAY, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 6, dpd.amount, 0)) AS JUN, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (4, 5, 6), dpd.amount, 0)) AS '2/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 7, dpd.amount, 0)) AS JUL, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 8, dpd.amount, 0)) AS AUGUST, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 9, dpd.amount, 0)) AS SEPT, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (7, 8, 9), dpd.amount, 0)) AS '3/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 10, dpd.amount, 0)) AS OCTUBER, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 11, dpd.amount, 0)) AS NOV, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 12, dpd.amount, 0)) AS DIC, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (10, 11, 12), dpd.amount, 0)) AS '4/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), dpd.amount, 0)) AS 'G. TOTAL', \n"
                        + "    'MATERIAL' AS expense_group \n"
                        + "FROM voucher_quetzales AS vd \n"
                        + "JOIN purchases_vouchers_quetzales_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                        + "JOIN purchases_quetzales pd ON pd.id = pvdv.id_purchase \n"
                        + "JOIN providers pr ON pd.provider = pr.id \n"
                        + "JOIN expense_category c ON pr.category = c.id \n"
                        + "JOIN description_purchases_quetzales dpd ON dpd.purchase = pd.id \n"
                        + "WHERE");

                if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'KCI' GROUP BY pr.name ");
                }

                if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'SQA' GROUP BY pr.name ");
                }

                if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' GROUP BY pr.name ");
                }

                if (!conditions.isEmpty()) {
                    sqlQuery.append(String.join(" AND ", conditions));
                    conditions.clear();
                }

                sqlQuery.append(" UNION ALL ");

                sqlQuery.append("SELECT 'TOTAL MATERIALES' AS name, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 1, dpd.amount, 0)) AS JAN, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 2, dpd.amount, 0)) AS FEB, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 3, dpd.amount, 0)) AS MAR, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3), dpd.amount, 0)) AS '1/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 4, dpd.amount, 0)) AS APR, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 5, dpd.amount, 0)) AS MAY, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 6, dpd.amount, 0)) AS JUN, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (4, 5, 6), dpd.amount, 0)) AS '2/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 7, dpd.amount, 0)) AS JUL, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 8, dpd.amount, 0)) AS AUGUST, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 9, dpd.amount, 0)) AS SEPT, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (7, 8, 9), dpd.amount, 0)) AS '3/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 10, dpd.amount, 0)) AS OCTUBER, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 11, dpd.amount, 0)) AS NOV, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) = 12, dpd.amount, 0)) AS DIC, \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (10, 11, 12), dpd.amount, 0)) AS '4/ 4분기 / SUBDIVISION', \n"
                        + "    SUM(IF(MONTH(vd.payment_date) IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), dpd.amount, 0)) AS 'G. TOTAL', \n"
                        + "    'MATERIAL' AS expense_group \n"
                        + "FROM voucher_quetzales AS vd \n"
                        + "JOIN purchases_vouchers_quetzales_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                        + "JOIN purchases_quetzales pd ON pd.id = pvdv.id_purchase \n"
                        + "JOIN providers pr ON pd.provider = pr.id \n"
                        + "JOIN expense_category c ON pr.category = c.id \n"
                        + "JOIN description_purchases_quetzales dpd ON dpd.purchase = pd.id \n"
                        + "WHERE");

                if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'KCI' GROUP BY expense_group ");
                }

                if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'SQA' GROUP BY expense_group ");
                }

                if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' GROUP BY expense_group ");
                }

                if (!conditions.isEmpty()) {
                    sqlQuery.append(String.join(" AND ", conditions));
                    conditions.clear();
                }

            }

            if (bothRadio.isSelected()) {
                sqlQuery = new StringBuilder(
                        "  SELECT			\n"
                        + "                                                  name,\n"
                        + "                                                  SUM(JAN) AS JAN,\n"
                        + "                                                  SUM(FEB) AS FEB,\n"
                        + "                                                  SUM(MAR) AS MAR,\n"
                        + "                                                  SUM(JAN + FEB + MAR) AS '1/ 4분기 / SUBDIVISION',\n"
                        + "                                                  SUM(APR) AS APR,\n"
                        + "                                                  SUM(MAY) AS MAY,\n"
                        + "                                                  SUM(JUN) AS JUN,\n"
                        + "                                                  SUM(APR  +  MAY + JUN) AS '2/ 4분기 / SUBDIVISION',\n"
                        + "                                                  SUM(JUL) AS JUL,\n"
                        + "                                                  SUM(AUGUST) AS AUGUST,\n"
                        + "                                                  SUM(SEPT) AS SEPT,\n"
                        + "                                                  SUM(JUL + AUGUST + SEPT) AS '3/ 4분기 / SUBDIVISION',\n"
                        + "                                                  SUM(OCTUBER) AS OCTUBER,\n"
                        + "                                                  SUM(NOV) AS NOV,\n"
                        + "                                                  SUM(DIC) AS DIC,\n"
                        + "                                                  SUM(OCTUBER + NOV + DIC) AS '4/ 4분기 / SUBDIVISION',\n"
                        + "                                                  SUM(JAN + FEB + MAR + APR + MAY + JUN + JUL + AUGUST + SEPT + OCTUBER + NOV + DIC) AS 'G. TOTAL',\n"
                        + "                                                  MAX(expense_group) AS expense_group\n"
                        + "                                              FROM (\n"
                        + "                                                  SELECT\n"
                        + "                                                      pr.name AS name,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=1, dpd.amount / 7.55, 0)) AS JAN,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=2, dpd.amount / 7.55, 0)) AS FEB,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=3, dpd.amount / 7.55, 0)) AS MAR,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=4, dpd.amount / 7.55, 0)) AS APR,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=5, dpd.amount / 7.55, 0)) AS MAY,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=6, dpd.amount / 7.55, 0)) AS JUN,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=7, dpd.amount / 7.55, 0)) AS JUL,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=8, dpd.amount / 7.55, 0)) AS AUGUST,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=9, dpd.amount / 7.55, 0)) AS SEPT,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=10, dpd.amount / 7.55, 0)) AS OCTUBER,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=11, dpd.amount / 7.55, 0)) AS NOV,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=12, dpd.amount / 7.55, 0)) AS DIC,\n"
                        + "                                                      'FIXED' as expense_group\n"
                        + "                                                  FROM voucher_quetzales AS vd \n"
                        + "                                              JOIN purchases_vouchers_quetzales_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                        + "                                              JOIN purchases_quetzales pd ON pd.id = pvdv.id_purchase \n"
                        + "                                              JOIN providers pr ON pd.provider = pr.id \n"
                        + "                                              JOIN expense_category c ON pr.category = c.id \n"
                        + "                                              JOIN description_purchases_quetzales dpd ON dpd.purchase = pd.id WHERE"
                );
                if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'KCI' GROUP BY pr.name ");
                }

                if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'SQA' GROUP BY pr.name ");
                }

                if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' GROUP BY pr.name ");
                }

                if (!conditions.isEmpty()) {
                    sqlQuery.append(String.join(" AND ", conditions));
                    conditions.clear();
                }

                sqlQuery.append(" UNION ALL ");

                sqlQuery.append(
                        " SELECT\n"
                        + "                                                      pr.name AS name,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=1, dpd.amount, 0)) AS JAN,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=2, dpd.amount, 0)) AS FEB,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=3, dpd.amount, 0)) AS MAR,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=4, dpd.amount, 0)) AS APR,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=5, dpd.amount, 0)) AS MAY,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=6, dpd.amount, 0)) AS JUN,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=7, dpd.amount, 0)) AS JUL,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=8, dpd.amount, 0)) AS AUGUST,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=9, dpd.amount, 0)) AS SEPT,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=10, dpd.amount, 0)) AS OCTUBER,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=11, dpd.amount, 0)) AS NOV,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=12, dpd.amount, 0)) AS DIC,\n"
                        + "                                                       'FIXED' as expense_group\n"
                        + "                                                 FROM voucher_dollars AS vd \n"
                        + "                                              JOIN purchases_vouchers_dollars_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                        + "                                              JOIN purchases_dollars pd ON pd.id = pvdv.id_purchase \n"
                        + "                                              JOIN providers pr ON pd.provider = pr.id \n"
                        + "                                              JOIN expense_category c ON pr.category = c.id \n"
                        + "                                              JOIN description_purchases_dollars dpd ON dpd.purchase = pd.id WHERE "
                );

                if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'KCI' GROUP BY pr.name ");
                }

                if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'SQA' GROUP BY pr.name ");
                }

                if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' GROUP BY pr.name ");
                }

                if (!conditions.isEmpty()) {
                    sqlQuery.append(String.join(" AND ", conditions));
                    conditions.clear();
                }

                sqlQuery.append(
                        " ) AS fixed_combine_data_dollars_quetzles\n"
                        + " GROUP BY name "
                );

                sqlQuery.append("UNION ALL");

                sqlQuery.append(
                        "   SELECT			\n"
                        + "                                                  name,\n"
                        + "                                                  SUM(JAN) AS JAN,\n"
                        + "                                                  SUM(FEB) AS FEB,\n"
                        + "                                                  SUM(MAR) AS MAR,\n"
                        + "                                                  SUM(JAN + FEB + MAR) AS '1/ 4분기 / SUBDIVISION',\n"
                        + "                                                  SUM(APR) AS APR,\n"
                        + "                                                  SUM(MAY) AS MAY,\n"
                        + "                                                  SUM(JUN) AS JUN,\n"
                        + "                                                  SUM(APR  +  MAY + JUN) AS '2/ 4분기 / SUBDIVISION',\n"
                        + "                                                  SUM(JUL) AS JUL,\n"
                        + "                                                  SUM(AUGUST) AS AUGUST,\n"
                        + "                                                  SUM(SEPT) AS SEPT,\n"
                        + "                                                  SUM(JUL + AUGUST + SEPT) AS '3/ 4분기 / SUBDIVISION',\n"
                        + "                                                  SUM(OCTUBER) AS OCTUBER,\n"
                        + "                                                  SUM(NOV) AS NOV,\n"
                        + "                                                  SUM(DIC) AS DIC,\n"
                        + "                                                  SUM(OCTUBER + NOV + DIC) AS '4/ 4분기 / SUBDIVISION',\n"
                        + "                                                  SUM(JAN + FEB + MAR + APR + MAY + JUN + JUL + AUGUST + SEPT + OCTUBER + NOV + DIC) AS 'G. TOTAL',\n"
                        + "                                                  MAX(expense_group) AS expense_group\n"
                        + "                                              FROM (\n"
                        + "                                                  SELECT\n"
                        + "                                                      'TOTAL FIJOS' AS name,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=1, dpd.amount / 7.55, 0)) AS JAN,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=2, dpd.amount / 7.55, 0)) AS FEB,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=3, dpd.amount / 7.55, 0)) AS MAR,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=4, dpd.amount / 7.55, 0)) AS APR,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=5, dpd.amount / 7.55, 0)) AS MAY,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=6, dpd.amount / 7.55, 0)) AS JUN,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=7, dpd.amount / 7.55, 0)) AS JUL,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=8, dpd.amount / 7.55, 0)) AS AUGUST,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=9, dpd.amount / 7.55, 0)) AS SEPT,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=10, dpd.amount / 7.55, 0)) AS OCTUBER,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=11, dpd.amount / 7.55, 0)) AS NOV,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=12, dpd.amount / 7.55, 0)) AS DIC,\n"
                        + "                                                      'FIXED' as expense_group\n"
                        + "                                                  FROM voucher_quetzales AS vd \n"
                        + "                                              JOIN purchases_vouchers_quetzales_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                        + "                                              JOIN purchases_quetzales pd ON pd.id = pvdv.id_purchase \n"
                        + "                                              JOIN providers pr ON pd.provider = pr.id \n"
                        + "                                              JOIN expense_category c ON pr.category = c.id \n"
                        + "                                              JOIN description_purchases_quetzales dpd ON dpd.purchase = pd.id WHERE "
                );

                if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'KCI' GROUP BY pr.name ");
                }

                if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'SQA' GROUP BY pr.name ");
                }

                if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' GROUP BY pr.name ");
                }

                if (!conditions.isEmpty()) {
                    sqlQuery.append(String.join(" AND ", conditions));
                    conditions.clear();
                }

                sqlQuery.append(" UNION ALL ");

                sqlQuery.append(
                        "SELECT\n"
                        + "                                                      'TOTAL FIJOS' AS name,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=1, dpd.amount, 0)) AS JAN,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=2, dpd.amount, 0)) AS FEB,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=3, dpd.amount, 0)) AS MAR,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=4, dpd.amount, 0)) AS APR,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=5, dpd.amount, 0)) AS MAY,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=6, dpd.amount, 0)) AS JUN,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=7, dpd.amount, 0)) AS JUL,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=8, dpd.amount, 0)) AS AUGUST,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=9, dpd.amount, 0)) AS SEPT,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=10, dpd.amount, 0)) AS OCTUBER,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=11, dpd.amount, 0)) AS NOV,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=12, dpd.amount, 0)) AS DIC,\n"
                        + "                                                       'FIXED' as expense_group\n"
                        + "                                                 FROM voucher_dollars AS vd \n"
                        + "                                              JOIN purchases_vouchers_dollars_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                        + "                                              JOIN purchases_dollars pd ON pd.id = pvdv.id_purchase \n"
                        + "                                              JOIN providers pr ON pd.provider = pr.id \n"
                        + "                                              JOIN expense_category c ON pr.category = c.id \n"
                        + "                                              JOIN description_purchases_dollars dpd ON dpd.purchase = pd.id WHERE "
                );

                if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'KCI' GROUP BY pr.name ");
                }

                if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' AND c.linked_to = 'SQA' GROUP BY pr.name ");
                }

                if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'FIXED' GROUP BY pr.name ");
                }

                if (!conditions.isEmpty()) {
                    sqlQuery.append(String.join(" AND ", conditions));
                    conditions.clear();
                }

                sqlQuery.append(" ) AS fixed_combine_data_dollars_quetzles\n"
                        + "  GROUP BY name");
                /**
                 * Fin consulta a gastos fijos y agrupacion de totales para
                 * gastos fijos
                 */

                /**
                 * UNION ALL GET MATERIAL EXPENSE
                 */
                sqlQuery.append(" UNION ALL");

                sqlQuery.append(
                        "   SELECT			\n"
                        + "                                                  name,\n"
                        + "                                                  SUM(JAN) AS JAN,\n"
                        + "                                                  SUM(FEB) AS FEB,\n"
                        + "                                                  SUM(MAR) AS MAR,\n"
                        + "                                                  SUM(JAN + FEB + MAR) AS '1/ 4분기 / SUBDIVISION',\n"
                        + "                                                  SUM(APR) AS APR,\n"
                        + "                                                  SUM(MAY) AS MAY,\n"
                        + "                                                  SUM(JUN) AS JUN,\n"
                        + "                                                  SUM(APR  +  MAY + JUN) AS '2/ 4분기 / SUBDIVISION',\n"
                        + "                                                  SUM(JUL) AS JUL,\n"
                        + "                                                  SUM(AUGUST) AS AUGUST,\n"
                        + "                                                  SUM(SEPT) AS SEPT,\n"
                        + "                                                  SUM(JUL + AUGUST + SEPT) AS '3/ 4분기 / SUBDIVISION',\n"
                        + "                                                  SUM(OCTUBER) AS OCTUBER,\n"
                        + "                                                  SUM(NOV) AS NOV,\n"
                        + "                                                  SUM(DIC) AS DIC,\n"
                        + "                                                  SUM(OCTUBER + NOV + DIC) AS '4/ 4분기 / SUBDIVISION',\n"
                        + "                                                  SUM(JAN + FEB + MAR + APR + MAY + JUN + JUL + AUGUST + SEPT + OCTUBER + NOV + DIC) AS 'G. TOTAL',\n"
                        + "                                                  MAX(expense_group) AS expense_group\n"
                        + "                                              FROM (\n"
                        + "                                                  SELECT\n"
                        + "                                                      pr.name AS name,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=1, dpd.amount / 7.55, 0)) AS JAN,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=2, dpd.amount / 7.55, 0)) AS FEB,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=3, dpd.amount / 7.55, 0)) AS MAR,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=4, dpd.amount / 7.55, 0)) AS APR,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=5, dpd.amount / 7.55, 0)) AS MAY,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=6, dpd.amount / 7.55, 0)) AS JUN,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=7, dpd.amount / 7.55, 0)) AS JUL,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=8, dpd.amount / 7.55, 0)) AS AUGUST,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=9, dpd.amount / 7.55, 0)) AS SEPT,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=10, dpd.amount / 7.55, 0)) AS OCTUBER,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=11, dpd.amount / 7.55, 0)) AS NOV,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=12, dpd.amount / 7.55, 0)) AS DIC,\n"
                        + "                                                      'MATERIAL' as expense_group\n"
                        + "                                                FROM voucher_quetzales AS vd \n"
                        + "                         JOIN purchases_vouchers_quetzales_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                        + "                         JOIN purchases_quetzales pd ON pd.id = pvdv.id_purchase \n"
                        + "                         JOIN providers pr ON pd.provider = pr.id \n"
                        + "                         JOIN expense_category c ON pr.category = c.id \n"
                        + "                         JOIN description_purchases_quetzales dpd ON dpd.purchase = pd.id \n"
                        + "                         WHERE "
                );

                if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'KCI' GROUP BY pr.name ");
                }

                if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'SQA' GROUP BY pr.name ");
                }

                if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' GROUP BY pr.name ");
                }

                if (!conditions.isEmpty()) {
                    sqlQuery.append(String.join(" AND ", conditions));
                    conditions.clear();
                }

                sqlQuery.append(" UNION ALL ");

                sqlQuery.append(
                        "  SELECT\n"
                        + "                                                      pr.name AS name,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=1, dpd.amount, 0)) AS JAN,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=2, dpd.amount, 0)) AS FEB,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=3, dpd.amount, 0)) AS MAR,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=4, dpd.amount, 0)) AS APR,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=5, dpd.amount, 0)) AS MAY,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=6, dpd.amount, 0)) AS JUN,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=7, dpd.amount, 0)) AS JUL,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=8, dpd.amount, 0)) AS AUGUST,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=9, dpd.amount, 0)) AS SEPT,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=10, dpd.amount, 0)) AS OCTUBER,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=11, dpd.amount, 0)) AS NOV,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=12, dpd.amount, 0)) AS DIC,\n"
                        + "                                                       'MATERIAL' as expense_group\n"
                        + "                                                 FROM voucher_dollars AS vd \n"
                        + "                         JOIN purchases_vouchers_dollars_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                        + "                         JOIN purchases_dollars pd ON pd.id = pvdv.id_purchase \n"
                        + "                         JOIN providers pr ON pd.provider = pr.id \n"
                        + "                         JOIN expense_category c ON pr.category = c.id \n"
                        + "                         JOIN description_purchases_dollars dpd ON dpd.purchase = pd.id \n"
                        + "                         WHERE "
                );

                if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'KCI' GROUP BY pr.name ");
                }

                if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'SQA' GROUP BY pr.name ");
                }

                if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' GROUP BY pr.name ");
                }

                if (!conditions.isEmpty()) {
                    sqlQuery.append(String.join(" AND ", conditions));
                    conditions.clear();
                }

                sqlQuery.append(") AS combined_data\n"
                        + "  GROUP BY name");

                sqlQuery.append(" UNION ALL ");
                /**
                 * MATERIAL EXPENSE GROUP TOTAL
                 */

                sqlQuery.append(
                        " SELECT			\n"
                        + "                                                  name,\n"
                        + "                                                  SUM(JAN) AS JAN,\n"
                        + "                                                  SUM(FEB) AS FEB,\n"
                        + "                                                  SUM(MAR) AS MAR,\n"
                        + "                                                  SUM(JAN + FEB + MAR) AS '1/ 4분기 / SUBDIVISION',\n"
                        + "                                                  SUM(APR) AS APR,\n"
                        + "                                                  SUM(MAY) AS MAY,\n"
                        + "                                                  SUM(JUN) AS JUN,\n"
                        + "                                                  SUM(APR  +  MAY + JUN) AS '2/ 4분기 / SUBDIVISION',\n"
                        + "                                                  SUM(JUL) AS JUL,\n"
                        + "                                                  SUM(AUGUST) AS AUGUST,\n"
                        + "                                                  SUM(SEPT) AS SEPT,\n"
                        + "                                                  SUM(JUL + AUGUST + SEPT) AS '3/ 4분기 / SUBDIVISION',\n"
                        + "                                                  SUM(OCTUBER) AS OCTUBER,\n"
                        + "                                                  SUM(NOV) AS NOV,\n"
                        + "                                                  SUM(DIC) AS DIC,\n"
                        + "                                                  SUM(OCTUBER + NOV + DIC) AS '4/ 4분기 / SUBDIVISION',\n"
                        + "                                                  SUM(JAN + FEB + MAR + APR + MAY + JUN + JUL + AUGUST + SEPT + OCTUBER + NOV + DIC) AS 'G. TOTAL',\n"
                        + "                                                  MAX(expense_group) AS expense_group\n"
                        + "                                              FROM (\n"
                        + "                                                  SELECT\n"
                        + "                                                      'TOTAL MATERIALES' AS name,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=1, dpd.amount / 7.55, 0)) AS JAN,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=2, dpd.amount / 7.55, 0)) AS FEB,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=3, dpd.amount / 7.55, 0)) AS MAR,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=4, dpd.amount / 7.55, 0)) AS APR,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=5, dpd.amount / 7.55, 0)) AS MAY,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=6, dpd.amount / 7.55, 0)) AS JUN,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=7, dpd.amount / 7.55, 0)) AS JUL,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=8, dpd.amount / 7.55, 0)) AS AUGUST,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=9, dpd.amount / 7.55, 0)) AS SEPT,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=10, dpd.amount / 7.55, 0)) AS OCTUBER,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=11, dpd.amount / 7.55, 0)) AS NOV,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=12, dpd.amount / 7.55, 0)) AS DIC,\n"
                        + "                                                      'MATERIAL' as expense_group\n"
                        + "                                                FROM voucher_quetzales AS vd \n"
                        + "                         JOIN purchases_vouchers_quetzales_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                        + "                         JOIN purchases_quetzales pd ON pd.id = pvdv.id_purchase \n"
                        + "                         JOIN providers pr ON pd.provider = pr.id \n"
                        + "                         JOIN expense_category c ON pr.category = c.id \n"
                        + "                         JOIN description_purchases_quetzales dpd ON dpd.purchase = pd.id \n"
                        + "                         WHERE "
                );

                if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'KCI' GROUP BY pr.name ");
                }

                if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'SQA' GROUP BY pr.name ");
                }

                if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' GROUP BY pr.name ");
                }

                if (!conditions.isEmpty()) {
                    sqlQuery.append(String.join(" AND ", conditions));
                    conditions.clear();
                }

                sqlQuery.append(" UNION ALL ");

                sqlQuery.append(
                        "  SELECT\n"
                        + "                                                      'TOTAL MATERIALES' AS name,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=1, dpd.amount, 0)) AS JAN,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=2, dpd.amount, 0)) AS FEB,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=3, dpd.amount, 0)) AS MAR,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=4, dpd.amount, 0)) AS APR,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=5, dpd.amount, 0)) AS MAY,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=6, dpd.amount, 0)) AS JUN,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=7, dpd.amount, 0)) AS JUL,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=8, dpd.amount, 0)) AS AUGUST,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=9, dpd.amount, 0)) AS SEPT,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=10, dpd.amount, 0)) AS OCTUBER,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=11, dpd.amount, 0)) AS NOV,\n"
                        + "                                                      SUM(IF(MONTH(vd.payment_date)=12, dpd.amount, 0)) AS DIC,\n"
                        + "                                                       'MATERIAL' as expense_group\n"
                        + "                                                 FROM voucher_dollars AS vd \n"
                        + "                         JOIN purchases_vouchers_dollars_vinculation AS pvdv ON pvdv.id_voucher = vd.id \n"
                        + "                         JOIN purchases_dollars pd ON pd.id = pvdv.id_purchase \n"
                        + "                         JOIN providers pr ON pd.provider = pr.id \n"
                        + "                         JOIN expense_category c ON pr.category = c.id \n"
                        + "                         JOIN description_purchases_dollars dpd ON dpd.purchase = pd.id \n"
                        + "                         WHERE "
                );

                if (apparelKCICheckBox.isSelected() && !sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'KCI' GROUP BY pr.name ");
                }

                if (!apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' AND c.linked_to = 'SQA' GROUP BY pr.name ");
                }

                if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    conditions.add(" YEAR(vd.payment_date) = ? AND vd.status = 'SUCCESSFUL' AND c.expense_group = 'MATERIAL' GROUP BY pr.name ");
                }

                if (!conditions.isEmpty()) {
                    sqlQuery.append(String.join(" AND ", conditions));
                    conditions.clear();
                }

                sqlQuery.append(
                        " ) AS combined_data\n"
                        + "                      GROUP BY name "
                );

                if (apparelKCICheckBox.isSelected() && sqaCheckbox.isSelected()) {
                    sqlQuery.append("UNION ALL ");
                    sqlQuery.append("SELECT 'BUYERS BILLING' as name,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) = 1, bb.amount, 0)) AS JAN,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) = 2, bb.amount, 0)) AS FEB,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) = 3, bb.amount, 0)) AS MAR,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) IN (1, 2, 3), bb.amount, 0)) AS '1/ 4분기 / SUBDIVISION',\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) = 4, bb.amount, 0)) AS APR,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) = 5, bb.amount, 0)) AS MAY,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) = 6, bb.amount, 0)) AS JUN,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) IN (4, 5, 6), bb.amount, 0)) AS '2/ 4분기 / SUBDIVISION',\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) = 7, bb.amount, 0)) AS JUL,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) = 8, bb.amount, 0)) AS AUGUST,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) = 9, bb.amount, 0)) AS SEPT,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) IN (7, 8, 9), bb.amount, 0)) AS '3/ 4분기 / SUBDIVISION',\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) = 10, bb.amount, 0)) AS OCTUBER,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) = 11, bb.amount, 0)) AS NOV,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) = 12, bb.amount, 0)) AS DIC,\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) IN (10, 11, 12), bb.amount, 0)) AS '4/ 4분기 / SUBDIVISION',\n"
                            + "    SUM(IF(MONTH(bb.emissionDate) IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), bb.amount, 0)) AS 'G. TOTAL',\n"
                            + "    'SALES' AS expense_group\n"
                            + "FROM billing_buyers AS BB\n"
                            + "WHERE YEAR(bb.emissionDate) = ? \n"
                            + "GROUP BY expense_group");
                }

            }

        }
    }
