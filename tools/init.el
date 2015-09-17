(custom-set-variables
 ;; custom-set-variables was added by Custom.
 ;; If you edit it by hand, you could mess it up, so be careful.
 ;; Your init file should contain only one such instance.
 ;; If there is more than one, they won't work right.
 '(ansi-color-names-vector ["#2d3743" "#ff4242" "#74af68" "#dbdb95" "#34cae2" "#008b8b" "#00ede1" "#e1e1e0"])
 '(auto-indent-next-pair-timer-geo-mean (quote ((default 0.0005 0))))
 '(column-number-mode t)
 '(custom-enabled-themes (quote (deeper-blue)))
 '(custom-safe-themes (quote ("71efabb175ea1cf5c9768f10dad62bb2606f41d110152f4ace675325d28df8bd" default)))
 '(display-battery-mode t)
 '(display-time-mode t)
 ;'(ede-project-directories (quote ("c://Sync//")))
 '(fringe-mode (quote (nil . 0)) nil (fringe))
 '(global-ede-mode t)
 '(indicate-buffer-boundaries (quote left))
 '(scroll-bar-mode nil)
 '(send-mail-function (quote smtpmail-send-it))
 '(show-paren-mode t)
 '(size-indication-mode t)
 '(tool-bar-mode nil))
(custom-set-faces
 ;; custom-set-faces was added by Custom.
 ;; If you edit it by hand, you could mess it up, so be careful.
 ;; Your init file should contain only one such instance.
 ;; If there is more than one, they won't work right.
 )
  
(package-initialize)
(setq-default inhibit-startup-screen t)

;; stops notification beeps coming with scrolling up/down.
(setq visible-bell t)

;; stop Emacs from leaving "foo~" (backup) files all
;; over the place.
(setq make-backup-files nil)

;; Use only spaces (no tabs at all).
(setq-default indent-tabs-mode nil)

;; Always show column numbers.
(setq-default column-number-mode t)

;; Display full pathname for files.
;(add-hook 'find-file-hooks
;          '(lambda ()
;             (setq mode-line-buffer-identification 'buffer-file-truename)))

;; For easier regex search/replace.
(defalias 'qrr 'query-replace-regexp)

(load-theme 'deeper-blue t)
(set-background-color "#383838")

(add-hook 'cider-repl-mode-hook #'company-mode)
(add-hook 'cider-mode-hook #'company-mode)
(add-hook 'cider-repl-mode-hook #'paredit-mode)

(global-set-key "\M-j" 'cider-jack-in)
(global-set-key "\M-k" 'paredit-mode)
(global-set-key "\M-l" 'company-mode)
(global-set-key "\M-n" 'scroll-up-line)
(global-set-key "\M-p" 'scroll-down-line)
(global-set-key (kbd "<C-tab>") 'other-window)
(global-set-key "[" "(")
(global-set-key "]" ")")

(require 'package)
(add-to-list 'package-archives
            '("melpa" . "http://melpa.milkbox.net/packages/"))