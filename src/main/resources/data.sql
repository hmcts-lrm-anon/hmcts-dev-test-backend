INSERT INTO task (title, description, status, due_date_time) VALUES
('Review urgent application', 'Check eligibility and verify documents', 'OPEN', '2025-09-17 12:30:00'),
('Update task status', 'Change status to IN_PROGRESS after review', 'OPEN', '2025-09-25 11:00:00'),
('Draft order', 'Prepare draft order for judge approval', 'OPEN', '2025-09-30 15:00:00'),
('Review appeal', 'Review documents submitted for appeal', 'OPEN', '2025-10-12 12:30:00');

INSERT INTO task (title, description, status, due_date_time) VALUES
('Prepare hearing bundle', 'Compile all relevant documents for the hearing', 'IN_PROGRESS', '2025-09-20 09:00:00'),
('Schedule mediation', 'Arrange mediation session for parties', 'IN_PROGRESS', '2025-09-28 14:00:00'),
('Send reminder to parties', 'Send reminder email for document submission', 'IN_PROGRESS', '2025-10-08 10:30:00'),
('Prepare summary', 'Summarize task details for reporting', 'IN_PROGRESS', '2025-10-15 13:30:00');

INSERT INTO task (title, description, status, due_date_time) VALUES
('Send notification to parties', 'Notify all parties of the next hearing date', 'COMPLETED', '2025-09-22 10:00:00'),
('Verify evidence', 'Check submitted evidence for completeness', 'COMPLETED', '2025-10-02 16:00:00'),
('Close task', 'Mark task as closed after final order', 'COMPLETED', '2025-10-10 11:30:00')
  ON CONFLICT DO NOTHING;
